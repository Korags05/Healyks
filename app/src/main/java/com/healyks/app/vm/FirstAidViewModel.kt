package com.healyks.app.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.healyks.app.data.local.FirstAidItem

class FirstAidViewModel : ViewModel() {

    val firstAidItems = listOf(
        FirstAidItem("Injuries", "Burn", "Cool the burn under running water for at least 10 minutes. Avoid ice, ointments, or butter. Cover with a clean, non-stick cloth.", "If the burn blisters, covers a large area, or is on the face, hands, or joints."),
        FirstAidItem("Injuries", "Fracture", "Immobilize the injured area, avoid moving it, and apply a splint if possible. Use an ice pack wrapped in a cloth to reduce swelling.", "Immediate medical attention for visible deformity, severe pain, or inability to move the limb."),
        FirstAidItem("Injuries", "Cut/Wound", "Rinse with clean water, apply gentle pressure to stop bleeding, and cover with a sterile bandage. Apply antiseptic cream if available.", "Deep wounds, heavy bleeding, or signs of infection (redness, swelling, pus)."),
        FirstAidItem("Injuries", "Sprain/Strain", "Use the RICE method — Rest, Ice, Compression, Elevation.", "If swelling or pain persists for more than 48 hours."),
        FirstAidItem("Injuries", "Bruise", "Apply an ice pack wrapped in a cloth for 10-15 minutes every hour to reduce swelling.", "If severe pain or swelling increases."),
        FirstAidItem("Breathing Issues", "Choking", "Encourage the person to cough. If they cannot breathe, perform the Heimlich maneuver and call emergency services.", "If the person turns blue or becomes unconscious."),
        FirstAidItem("Breathing Issues", "Asthma Attack", "Help them sit up and use their prescribed inhaler. Encourage slow, deep breaths.", "If symptoms don’t improve within 15 minutes or breathing becomes more difficult."),
        FirstAidItem("Breathing Issues", "Drowning", "Remove the person from water and check for breathing. If not breathing, begin CPR immediately.", "Always call emergency services after a drowning incident."),
        FirstAidItem("Breathing Issues", "Hyperventilation", "Encourage slow breathing into a paper bag or cupped hands to regulate carbon dioxide levels.", "If dizziness, chest pain, or confusion persists."),
        FirstAidItem("Cardiac Emergencies", "Heart Attack", "Help the person sit in a comfortable position, loosen clothing, and chew aspirin if not allergic. Keep calm and reassure them.", "Always call emergency services immediately."),
        FirstAidItem("Cardiac Emergencies", "Cardiac Arrest", "Begin CPR immediately. Push hard and fast in the center of the chest at 100-120 compressions per minute. Use an AED if available.", "Emergency services must be called immediately."),
        FirstAidItem("Cardiac Emergencies", "Stroke", "Use the FAST method — Face drooping, Arm weakness, Speech difficulty, Time to call emergency services.", "Always call emergency services immediately."),
        FirstAidItem("Allergic Reactions", "Anaphylaxis", "Use an epinephrine auto-injector (EpiPen) if available. Call emergency services immediately and keep the person lying down.", "Always call emergency services immediately."),
        FirstAidItem("Allergic Reactions", "Mild Allergy", "Administer an antihistamine and monitor symptoms. If swelling or breathing difficulties develop, seek medical attention.", "If symptoms worsen or swelling spreads."),
        FirstAidItem("Bites & Stings", "Snake Bite", "Keep the bitten limb immobilized and below heart level. Do not apply ice or suck out venom.", "Always call emergency services immediately."),
        FirstAidItem("Bites & Stings", "Insect Sting", "Remove the stinger by scraping with a card. Apply ice and take an antihistamine if swelling occurs.", "If the person shows signs of a severe allergic reaction."),
        FirstAidItem("Bites & Stings", "Animal Bite", "Clean the wound with soap and water and apply antiseptic. Cover with a clean bandage.", "If the bite is deep, bleeding heavily, or from an unknown animal."),
        FirstAidItem("Environmental Injuries", "Electric Shock", "Turn off the power source if safe. Avoid touching the person until power is off. Begin CPR if they are not breathing.", "Always call emergency services."),
        FirstAidItem("Environmental Injuries", "Heatstroke", "Move to a cool area, remove excess clothing, and apply cold cloths or ice packs to armpits and neck.", "If confusion, rapid heartbeat, or unconsciousness occurs."),
        FirstAidItem("Environmental Injuries", "Hypothermia", "Move to a warm area, remove wet clothing, and wrap in warm blankets. Avoid direct heat like hot water.", "If shivering stops, confusion, or drowsiness occurs."),
        FirstAidItem("Poisoning & Overdose", "Food Poisoning", "Drink plenty of fluids and rest. Avoid solid food until symptoms improve.", "Seek medical help if severe vomiting, dehydration, or high fever occurs."),
        FirstAidItem("Poisoning & Overdose", "Drug Overdose", "Keep the person awake and breathing. Do not induce vomiting unless instructed.", "Call emergency services immediately."),
        FirstAidItem("Eye, Ear & Nose Issues", "Foreign Object in Eye", "Do not rub the eye. Rinse with clean water or saline solution.", "Seek medical help if pain, redness, or vision issues persist."),
        FirstAidItem("Eye, Ear & Nose Issues", "Nose Blockage (Foreign Object)", "Do not try to remove the object with tweezers. Encourage gentle nose-blowing.", "If difficulty breathing or pain occurs, seek medical attention."),
        FirstAidItem("Elderly Health & Care", "Preventing Falls", "Ensure proper lighting, remove tripping hazards, and use handrails for support.", "Seek medical help if a fall results in severe pain or immobility."),
        FirstAidItem("General Injuries", "Sunburn", "Apply aloe vera or a moisturizing lotion. Avoid further sun exposure and stay hydrated.", "If blisters, fever, or dehydration symptoms appear."),
        FirstAidItem("General Injuries", "Frostbite", "Warm the affected area slowly with lukewarm water. Avoid rubbing the skin.", "If skin turns white, waxy, or numb, seek medical help immediately.")

    )

    var selectedItem by mutableStateOf<FirstAidItem?>(null)

    fun selectItem(item: FirstAidItem) {
        selectedItem = item
    }

}