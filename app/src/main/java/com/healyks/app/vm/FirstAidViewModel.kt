package com.healyks.app.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.healyks.app.data.local.FirstAidItem

class FirstAidViewModel : ViewModel() {

    val firstAidItems = listOf(
        FirstAidItem(
            "Injuries",
            "Burn",
            "Cool the burn under running water for at least 10 minutes. Avoid ice, ointments, or butter. Cover with a clean, non-stick cloth.",
            "If the burn blisters, covers a large area, or is on the face, hands, or joints."
        ),
        FirstAidItem(
            "Injuries",
            "Fracture",
            "Immobilize the injured area, avoid moving it, and apply a splint if possible. Use an ice pack wrapped in a cloth to reduce swelling.",
            "Immediate medical attention for visible deformity, severe pain, or inability to move the limb."
        ),
        FirstAidItem(
            "Injuries",
            "Cut/Wound",
            "Rinse with clean water, apply gentle pressure to stop bleeding, and cover with a sterile bandage. Apply antiseptic cream if available.",
            "Deep wounds, heavy bleeding, or signs of infection (redness, swelling, pus)."
        ),
        FirstAidItem(
            "Injuries",
            "Sprain/Strain",
            "Use the RICE method — Rest, Ice, Compression, Elevation.",
            "If swelling or pain persists for more than 48 hours."
        ),
        FirstAidItem(
            "Injuries",
            "Bruise",
            "Apply an ice pack wrapped in a cloth for 10-15 minutes every hour to reduce swelling.",
            "If severe pain or swelling increases."
        ),
        FirstAidItem(
            "Injuries",
            "Dislocation",
            "Keep the joint immobile and support it with a sling or soft padding. Apply an ice",
            "Immediate medical attention for visible joint displacement."
        ),
        FirstAidItem(
            "Breathing Issues",
            "Choking",
            "Use the RICE method — Rest, Ice, Compression, Elevation.",
            "If swelling or pain persists for more than 48 hours."
        ),
        FirstAidItem(
            "Breathing Issues",
            "Asthma Attack",
            "Help them sit up and use their prescribed inhaler. Encourage slow, deep breaths.",
            "If symptoms don’t improve within 15 minutes or breathing becomes more difficult."
        ),
        FirstAidItem(
            "Breathing Issues",
            "Drowning",
            "Remove the person from water and check for breathing. If not breathing, begin CPR immediately.",
            "Always call emergency services after a drowning incident."
        ),
        FirstAidItem(
            "Breathing Issues",
            "Hyperventilation",
            "Encourage slow breathing into a paper bag or cupped hands to regulate carbon dioxide levels.",
            "If dizziness, chest pain, or confusion persists."
        ),
    )

    var selectedItem by mutableStateOf<FirstAidItem?>(null)

    fun selectItem(item: FirstAidItem) {
        selectedItem = item
    }

}