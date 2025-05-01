package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intprog32.caterspot30.Adapters.CatererChatHeadAdapter
import com.intprog32.caterspot30.Data.CatererChatHead
import com.intprog32.caterspot30.Data.CatererData
import com.intprog32.caterspot30.Data.Message
import java.util.UUID

class SelectChatActivity : Activity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CatererChatHeadAdapter
    private val messages = mutableListOf<Message>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_chat)

        recyclerView = findViewById(R.id.caterer_chat_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Get the caterers from the CatererData
        val catererDataList = listOf(
            CatererData("ABC Catering", "Sea food and more.", R.drawable.sampleseafood),
            CatererData("XYZ Catering", "Buffet specialist.", R.drawable.shrimp),
            CatererData("MNO Delights", "Gourmet meals and desserts.", R.drawable.fish),
            CatererData("Jhonrel's Catering", "Sea food and more.", R.drawable.seafoodboil),
            CatererData("James' Catering", "Buffet specialist.", R.drawable.salmon),
            CatererData("Ian's Delights", "Gourmet meals and desserts.", R.drawable.shrimp),
            CatererData("Rianel's Catering", "Sea food and more.", R.drawable.sampleseafood)
        )
        // Convert CatererData to CatererChatHead
        val catererChatHeads = catererDataList.map { catererData ->
            CatererChatHead(
                catererId = UUID.randomUUID().toString(), // Generate a unique ID
                catererName = catererData.name,
                profileImageResId = catererData.imageResId,
                lastMessage = "" // You can add logic to get the last message here
            )
        }

        //Create the adapter
        adapter = CatererChatHeadAdapter(catererChatHeads, this)
        //Set the adapter
        recyclerView.adapter = adapter
    }
}