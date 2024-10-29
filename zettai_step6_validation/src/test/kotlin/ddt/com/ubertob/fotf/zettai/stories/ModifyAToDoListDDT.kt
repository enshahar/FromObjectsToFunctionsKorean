package ddt.com.ubertob.fotf.zettai.stories

import com.ubertob.pesticide.core.DDT
import ddt.com.ubertob.fotf.zettai.tooling.ToDoListOwner
import ddt.com.ubertob.fotf.zettai.tooling.ZettaiDDT
import ddt.com.ubertob.fotf.zettai.tooling.allActions

class ModifyAToDoListDDT : ZettaiDDT(allActions()) {

    val ann by NamedActor(::ToDoListOwner)
    val ben by NamedActor(::ToDoListOwner)

    @DDT
    fun `users can create a new list`() = ddtScenario {
        play(
            ann.`can create a new list called #listname`("myfirstlist"),
            ann.`can see #listname with #itemnames`(
                "myfirstlist", emptyList()
            )
        )
    }

    @DDT
    fun `the list owner can add new items`() = ddtScenario {
        setUp {
            ann.`starts with a list`("diy", emptyList())
        }.thenPlay(
            ann.`can add #item to the #listname`("paint the shelf", "diy"),
            ann.`can add #item to the #listname`("fix the gate", "diy"),
            ann.`can add #item to the #listname`("change the lock", "diy"),
            ann.`can see #listname with #itemnames`(
                "diy", listOf(
                    "fix the gate", "paint the shelf", "change the lock"
                )
            )
        )
    }

    @DDT
    fun `the list owner can rename a list`() = ddtScenario {
        setUp {
            ben.`starts with a list`("shopping", emptyList())
        }.thenPlay(
            ben.`can add #item to the #listname`("carrots", "shopping"),
            ben.`can rename the list #oldname as #newname`(
                origListName = "shopping",
                newListName = "grocery"
            ),
            ben.`can add #item to the #listname`("potatoes", "grocery"),
            ben.`can see #listname with #itemnames`(
                "grocery", listOf("carrots", "potatoes")
            )
        )
    }
}