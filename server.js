const express = require('express');
const { MongoClient } = require('mongodb');
const { ObjectId } = require('mongodb');
const cors = require('cors');
require('dotenv').config();

const app = express();
const port = process.env.PORT || 3000;
app.use(cors());
app.use(express.json());

const bodyParser = require('body-parser');
app.use(bodyParser.json());

const bcrypt = require('bcrypt');
const saltRounds = 10; 

// MongoDB connection setup
const uri = process.env.MONGODB_URI;
let itemsCollection;
let usersCollection;

async function connectToDB() {
  const client = new MongoClient(uri);
  try {
    await client.connect();
    const database = client.db('mydatabase');
    itemsCollection = database.collection('items');
    usersCollection = database.collection('regUsers'); // 👈 Add this line

    console.log("Connected to MongoDB Atlas");
  } catch (err) {
    console.error('Failed to connect to MongoDB:', err);
    process.exit(1);
  }
}

// Start the server after DB connection is established
async function startServer() {
  await connectToDB(); // Ensure DB connection before starting the server
  app.listen(port, '0.0.0.0',() => {
    console.log(`Server running at http://0.0.0.0:${port}`);
  });
}

// GET route to retrieve items

// Backend (Node.js)


app.get('/users/validate', async (req, res) =>  {
  try {
    const { email, password } = req.query;
    console.log('Attempting to validate user with email:', email); // Log the email being searched
    console.log('Provided password:', password); // Log the provided password (be cautious with logging sensitive info in production)

    const user = await usersCollection.findOne({ email: email });

    if (user) {
      console.log('User found:', user); // Log the user object if found
      console.log('Stored password:', user.password); // Log the stored password

      // Check if the user exists and the password matches
      if (user.password === password) { // Or use bcrypt.compare if hashing is implemented
        res.status(200).json({ message: 'Login successful' });
      } else {
        console.log('Password mismatch.');
        res.status(401).send('Invalid credentials');
      }
    } else {
      console.log('User not found with email:', email);
      res.status(401).send('Invalid credentials');
    }
  } catch (err) {
    console.error('Error fetching user:', err);
    res.status(500).send('Error fetching user');
  }
});

// GET route to fetch a single user by ID
app.get('/users/:id', async (req, res) => {
  const { id } = req.params;

  try {
    // Validate if the ID is a valid ObjectId
    if (!ObjectId.isValid(id)) {
      return res.status(400).send('Invalid user ID format');
    }

    const user = await usersCollection.findOne({ _id: new ObjectId(id) });

    if (user) {
      console.log('User found by ID:', user);
      // Remove sensitive data like password before sending
      const userWithoutPassword = { ...user };
      delete userWithoutPassword.password;
      res.status(200).json(userWithoutPassword);
    } else {
      console.log('User not found with ID:', id);
      res.status(404).send('User not found');
    }
  } catch (err) {
    console.error('Error fetching user by ID:', err);
    res.status(500).send('Error fetching user');
  }
});

// GET /users/profile?email=user@example.com
app.get('/users/profile', async (req, res) => {
  console.log('Received request with query:', req.query);
  const { email } = req.query;
  if (!email) {
    console.log('No email parameter provided.');
    return res.status(400).send('Email query parameter is required.');
  }

  try {
    const user = await usersCollection.findOne({ email: email });
    if (user) {
      delete user.password; // remove sensitive info before sending
      res.status(200).json(user);
    } else {
      res.status(404).send('User not found');
    }
  } catch (err) {
    res.status(500).send('Server error');
  }
});

// POST route to insert an item

app.post('/users', async (req, res) => {
  console.log('Received data:', req.body);
  const { firstName, lastName, email, password, phoneNumber } = req.body;

  if (!firstName || !lastName || !email || !password) {
    return res.status(400).send('Missing required user fields');
  }

  try {
    // Hash the password
    const hashedPassword = await bcrypt.hash(password, saltRounds);

    const newUser = {
      firstName,
      lastName,
      email,
      password: hashedPassword, // Store the hashed password
      phoneNumber: phoneNumber || 'N/A'
    };

    const result = await usersCollection.insertOne(newUser);

    console.log('User registered:', result);
    res.status(201).json(result);
  } catch (err) {
    console.error('Error registering user:', err);
    res.status(500).send('Error registering user');
  }
});
  

app.delete('/users/:id', async (req, res) => {
  const { id } = req.params;

  try {
    const result = await usersCollection.deleteOne({ _id: new ObjectId(id) });

    if (result.deletedCount === 1) {
      res.send(`User with ID ${id} deleted successfully.`);
    } else {
      res.status(404).send(`User with ID ${id} not found.`);
    }
  } catch (err) {
    console.error('Error deleting user:', err);
    res.status(500).send('Error deleting user');
  }
});

// PUT route to update user details
app.put('/users/:id', async (req, res) => {
  const { id } = req.params;  // Get the user ID from the URL
  const { firstName, lastName, email, password, phoneNumber } = req.body;  // Get new data from the request body

  // Ensure that at least one field to update is provided
  if (!firstName && !lastName && !email && !password && !phoneNumber) {
    return res.status(400).send('No fields provided for update');
  }

  try {
    // Construct an object with the fields to update
    const updatedUser = {};

    if (firstName) updatedUser.firstName = firstName;
    if (lastName) updatedUser.lastName = lastName;
    if (email) updatedUser.email = email;
    if (password) updatedUser.password = password;
    if (phoneNumber) updatedUser.phoneNumber = phoneNumber;

    // Update the user in the database
    const result = await usersCollection.updateOne(
      { _id: new ObjectId(id) },  // Find the user by ID
      { $set: updatedUser }  // Set the updated fields
    );

    if (result.matchedCount === 0) {
      return res.status(404).send(`User with ID ${id} not found`);
    }

    console.log('User updated:', result);
    res.status(200).json({ message: 'User updated successfully' });

  } catch (err) {
    console.error('Error updating user:', err);
    res.status(500).send('Error updating user');
  }
});

// Start the server
startServer();
