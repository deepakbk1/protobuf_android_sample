package com.deepak.protobuftest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addressBook = createAddressBookProtobuf()

        // Serialize AddressBook to ByteArray
        val bytes = addressBook.toByteArray()

        // Deserialize AddressBook ByteArray
        val myAddressBook = AddressBookProtos.AddressBook.parseFrom(bytes)
        println(myAddressBook)

        println("Protobuf byte size: ${bytes.size}")
        println("JSON byte size: ${SAMPLE_JSON.toByteArray().size}")
        textView.text= "Protobuf byte size: ${bytes.size}" +"\n JSON byte size: ${SAMPLE_JSON.toByteArray().size}"
    }

    private fun createAddressBookProtobuf(): AddressBookProtos.AddressBook {
        // building PhoneNumber objects
        val phoneHome = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("+49123456")
                .setType(AddressBookProtos.Person.PhoneType.HOME)
                .build()
        val phoneMobile = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("+49654321")
                .setType(AddressBookProtos.Person.PhoneType.MOBILE)
                .build()

        // building a Person object using phone data
        val person = AddressBookProtos.Person.newBuilder()
                .setId(1)
                .setName("Mohsen")
                .setEmail("info@deepak.com")
                .addAllPhones(listOf(phoneHome, phoneMobile))
                .build()

        // building an AddressBook object using person data
        return AddressBookProtos.AddressBook.newBuilder()
                .addAllPeople(listOf(person))
                .build()
    }

    companion object {
        private const val SAMPLE_JSON =
                """{
  "addressbook": [
    {
      "person": {
        "id": 1,
        "name": "Mohsen",
        "email": "info@deepak.com"
      }
    },
    {
      "phones": [
        {
          "phone": {
            "number": "+49123456",
            "type": "HOME"
          }
        },
        {
          "phone": {
            "number": "+49654321",
            "type": "MOBILE"
          }
        }
      ]
    }
  ]
}"""
    }
}
