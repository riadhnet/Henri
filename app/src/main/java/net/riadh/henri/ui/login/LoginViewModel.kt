package net.riadh.henri.ui.login

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import net.riadh.henri.model.RoomLogin


class LoginViewModel : ViewModel() {

    private lateinit var subscription: Disposable

    val userName = MutableLiveData<String>()
    val roomName = MutableLiveData<String>()
    val roomPassword = MutableLiveData<String>()

    val enabledInput: MutableLiveData<Boolean> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val roomClick: PublishSubject<RoomLogin> = PublishSubject.create()

    val database = FirebaseDatabase.getInstance()
    val myRef = database.reference
    private var auth = FirebaseAuth.getInstance()
    fun initData() {
        //init subscription
        enabledInput.value = false
        loadingVisibility.value = View.VISIBLE
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val user = auth.currentUser
                loadingVisibility.value = View.GONE
                enabledInput.value = true
            } else {
                // If sign in fails, display a message to the user.
                Log.w("test", "signInAnonymously:failure", task.exception)
                enabledInput.value = true
                loadingVisibility.value = View.GONE
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        //   subscription.dispose()
    }

    fun login() {


//        val key = myRef.child("room").key
//
//        val room = Room("testRoom", "1234", listOf(), key!!);
//
//        myRef.child("room").push().setValue(room)

        if (validateInput()) {

            val roomLogin = RoomLogin(
                userName.value.toString(),
                roomName.value.toString(),
                roomPassword.value.toString(),
                null
            )

            val roomInfoQuery = myRef.child("room");

            enabledInput.value = false
            loadingVisibility.value = View.VISIBLE
            roomInfoQuery.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    enabledInput.value = true
                    loadingVisibility.value = View.GONE
                    for (postSnapshot in dataSnapshot.children) {

                        if (postSnapshot.child("name").value?.equals(roomLogin.roomName)!! && postSnapshot.child("password").value?.equals(
                                roomLogin.roomPassword
                            )!!
                        ) {
                            roomLogin.uuid = postSnapshot.key
                            roomClick.onNext(
                                roomLogin
                            )
                            break
                        }
                        Log.i("test", "value =" + postSnapshot.value)
                        // Log.i("test", "size= " + postSnapshot.childrenCount)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    enabledInput.value = true
                    loadingVisibility.value = View.GONE
                    Log.w("test", "databaseError", databaseError.toException())
                    // ...
                }
            })
        }
    }

    private fun validateInput(): Boolean {
        return !TextUtils.isEmpty(userName.value) && !TextUtils.isEmpty(roomName.value) && !TextUtils.isEmpty(
            roomPassword.value
        )
    }

}