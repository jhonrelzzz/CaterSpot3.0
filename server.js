const bcrypt = require('bcrypt');
const express = require('express');
const { MongoClient } = require('mongodb');
const { ObjectId } = require('mongodb');
const cors = require('cors');
const app = express();
const port = process.env.PORT || 3000;
app.use(cors());
app.use(express.json());
require('dotenv').config();

const bodyParser = require('body-parser');
app.use(bodyParser.json());
const saltRounds = 10; 

// MongoDB connection setup
const uri = process.env.MONGODB_URI;

let usersCollection;
let caterersCollection;
let bookingsCollection;

async function connectToDB() {
  const client = new MongoClient(uri);
  try {
    await client.connect();
    const database = client.db('mydatabase');
    usersCollection = database.collection('regUsers');
    caterersCollection = database.collection('caterers');
    bookingsCollection = database.collection('bookings');
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

app.get('/users/validate', async (req, res) => {
  try {
    const { email, password } = req.query;
    console.log('Attempting to validate user with email:', email);
    console.log('Provided password:', password);

    const user = await usersCollection.findOne({ email: email });

    if (user) {
      console.log('User found:', user);
      console.log('Stored password (hashed):', user.password);

      // ✅ Use bcrypt to compare hashed password
      const passwordMatch = await bcrypt.compare(password, user.password);

      if (passwordMatch) {
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

app.post('/caterers', async (req, res) => {
  const { name, description, imageUrl } = req.body;

  if (!name || !description || !imageUrl) {
    return res.status(400).send('Missing required caterer data');
  }

  const newCaterer = {
    name,
    description,
    imageUrl,
  };

  try {
    const result = await caterersCollection.insertOne(newCaterer);
    res.status(201).json({ message: 'Caterer added', id: result.insertedId });
  } catch (err) {
    console.error('Error inserting caterer:', err);
    res.status(500).send('Error adding caterer');
  }
});

app.get('/caterers', async (req, res) => {
  try {
    const caterers = await caterersCollection.find({}).toArray();
    res.status(200).json(caterers);
  } catch (err) {
    console.error('Error fetching caterers:', err);
    res.status(500).send('Error fetching caterers');
  }
});

app.delete('/caterers/:id', async (req, res) => {
  const { id } = req.params;
  try {
    if (!ObjectId.isValid(id)) {
      return res.status(400).send('Invalid caterer ID');
    }
    const result = await caterersCollection.deleteOne({ _id: new ObjectId(id) });
    if (result.deletedCount === 1) {
      res.status(200).send(`Caterer with ID ${id} deleted successfully.`);
    } else {
      res.status(404).send(`Caterer with ID ${id} not found.`);
    }
  } catch (err) {
    console.error('Error deleting caterer:', err);
    res.status(500).send('Error deleting caterer');
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

// GET route to retrieve user by email
app.get('/users', async (req, res) => {
  const { email } = req.query;

  if (!email) {
    return res.status(400).send('Email is required');
  }

  try {
    const user = await usersCollection.findOne({ email });

    if (!user) {
      return res.status(404).send('User not found');
    }

    // Exclude password from response
    const { password, ...userWithoutPassword } = user;

    res.status(200).json(userWithoutPassword);
  } catch (err) {
    console.error('Error fetching user by email:', err);
    res.status(500).send('Error fetching user');
  }
});


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
  const { id } = req.params; // Get user ID from URL
  const { firstName, lastName, email, password, phoneNumber } = req.body; // Extract fields from request

  // Validate ObjectId
  if (!ObjectId.isValid(id)) {
    return res.status(400).send('Invalid user ID format');
  }

  // Build update object only with provided fields
  const updatedFields = {};

  if (firstName !== undefined) updatedFields.firstName = firstName;
  if (lastName !== undefined) updatedFields.lastName = lastName;
  if (email !== undefined) updatedFields.email = email;
  if (phoneNumber !== undefined) updatedFields.phoneNumber = phoneNumber;

  // Handle password separately: hash if provided
  if (password !== undefined) {
    try {
      const hashedPassword = await bcrypt.hash(password, saltRounds);
      updatedFields.password = hashedPassword;
    } catch (err) {
      console.error('Error hashing password:', err);
      return res.status(500).send('Error processing password');
    }
  }

  // Ensure there's at least one field to update
  if (Object.keys(updatedFields).length === 0) {
    return res.status(400).send('No fields provided for update');
  }

  try {
  const result = await usersCollection.updateOne(
    { _id: new ObjectId(id) },
    { $set: updatedFields }
  );

  if (result.matchedCount === 0) {
    return res.status(404).send(`User with ID ${id} not found`);
  }

  // Return the updated user object
  const updatedUser = await usersCollection.findOne({ _id: new ObjectId(id) });
  res.status(200).json(updatedUser);
  } catch (err) {
    console.error('Error updating user:', err);
    res.status(500).send('Error updating user');
  }
});

app.post('/bookings', async (req, res) => {
  const { event, pickDate, pickLocation, noOfGuests, customRequest } = req.body;

  if (!event || !pickDate || !pickLocation || !noOfGuests) {
    return res.status(400).send('Missing required booking fields');
  }

  const newBooking = {
    event,
    pickDate,
    pickLocation,
    noOfGuests,
    customRequest,
    createdAt: new Date()
  };

  try {
    const result = await bookingsCollection.insertOne(newBooking);
    res.status(201).json({ message: 'Booking created', id: result.insertedId });
  } catch (err) {
    console.error('Error creating booking:', err);
    res.status(500).send('Error creating booking');
  }
});

app.get('/bookings', async (req, res) => {
  try {
    const bookings = await bookingsCollection.find({}).toArray();
    res.status(200).json(bookings);
  } catch (err) {
    console.error('Error fetching bookings:', err);
    res.status(500).send('Error fetching bookings');
  }
});

// Start the server
startServer();
