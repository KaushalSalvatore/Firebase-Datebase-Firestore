package com.firebase.firebasedatabase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.firebasedatabase.data.Auther
import com.firebase.firebasedatabase.utils.NODE_AUTHERS
import com.google.firebase.database.*
import java.lang.Exception

class AuthersViewModel : ViewModel() {
    val dbAuther = FirebaseDatabase.getInstance().getReference(NODE_AUTHERS)

    private val _auther = MutableLiveData<List<Auther>>()
    val auther: LiveData<List<Auther>>
    get() = _auther

    private val _singleauther = MutableLiveData<Auther>()
    val singleauther: LiveData<Auther>
        get() = _singleauther

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
    get() = _result


    fun adddAuther(auther : Auther){
        auther.id = dbAuther.push().key
        dbAuther.child(auther.id!!).setValue(auther)
            .addOnCompleteListener {
                if(it.isSuccessful)
                {
                    _result.value=null
                }
                else{
                    _result.value=it.exception
                }
            }
    }

    private val childEventListener = object : ChildEventListener{
        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val auther = snapshot.getValue(Auther::class.java)
            auther?.id = snapshot.key
            _singleauther.value = auther
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val auther = snapshot.getValue(Auther::class.java)
            auther?.id = snapshot.key
            _singleauther.value = auther
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val auther = snapshot.getValue(Auther::class.java)
            auther?.id = snapshot.key
            auther?.isDeleted = true
            _singleauther.value = auther
        }

    }

    fun getRealtimeUpdates(){
        dbAuther.addChildEventListener(childEventListener)
    }

    fun fetchAuther(){
        dbAuther.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    val authers = mutableListOf<Auther>()
                    for(authorSnashot in snapshot.children)
                    {
                        val auther = authorSnashot.getValue(Auther::class.java)
                        auther?.id = authorSnashot.key
                        auther?.let { authers.add(it) }
                    }
                    _auther.value = authers
                }

            }
        })
    }

    fun updateAuther(auther: Auther)
    {
        dbAuther.child(auther.id!!).setValue(auther)
            .addOnCompleteListener {
                if(it.isSuccessful)
                {
                    _result.value=null
                }
                else{
                    _result.value=it.exception
                }
            }
    }

    fun deleteAuther(auther: Auther)
    {
        dbAuther.child(auther.id!!).setValue(null)
            .addOnCompleteListener {
                if(it.isSuccessful)
                {
                    _result.value=null
                }
                else{
                    _result.value=it.exception
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        dbAuther.removeEventListener(childEventListener)
    }

}