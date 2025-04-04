# Healyks: AI-Powered Rural Healthcare Assistant
  ![Healyks_Logo](https://github.com/user-attachments/assets/8d8caf92-d93d-458f-96f9-8698a0f4cf2f)
## 🌍 Project Overview

Healyks is an innovative Android application designed to address healthcare access challenges in underserved communities. By leveraging AI technologies and mobile accessibility, the app provides comprehensive health support for rural and resource-limited areas.

## 🚀 Key Features

### 1. AI-Powered Symptom Checker
- Analyze symptoms using Google Gemini API
- Provide personalized health recommendations

### 2. Medication Management
- Create personalized medication reminders
- Offline notification support
- Easy scheduling and tracking

### 3. Health Education
- Preventive health content
- Simplified health information
- Accessible on dashboard 

### 4. Emergency First-Aid Guidance
- Step-by-step emergency instructions
- Offline access to critical information
- Quick emergency service contact options

### 5. Periods and Ovulation Tracker
- Menstrual cycle tracking
- Next period prediction
- Fertility window identification

## 🛠 Technology Stack

- **Frontend:** Android (Jetpack Compose)
- **Backend:** Express.js
- **Database:** MongoDB
- **AI Integration:** Google Gemini API
- **Authentication:** Firebase Authentication
- **Deployment:** AWS EC2

## 🌱 Problem Statement

Addressing the lack of healthcare access in underserved communities by providing a comprehensive, user-friendly mobile health assistant.

## 🔧 Setup and Installation

### Prerequisites
- Android Studio
- Firebase Account

### Clone the Repository
```bash
git clone https://github.com/Korags05/Healyks.git
cd Healyks
```

### Configure Firebase
1. Add `google-services.json` to the `app` directory
2. Set up Firebase Authentication
3. Configure Gemini API credentials

### Environment Configuration
1. Create/Update `local.properties` file in the root directory with:
```properties```
  BASE_URL = "http://ec2-13-232-188-167.ap-south-1.compute.amazonaws.com:5000/"
  FIREBASE_TOKEN = "your_firebase_server_key"

### Build and Run
1. Open the project in Android Studio
2. Sync Gradle dependencies
3. Run the application on an emulator or physical device

## 📦 API Endpoints
- Authentication
- Symptom Analysis
- Health Education
- User details POST and GET

## 🔮 Future Roadmap
- Multi-language support
- Doctor and enterprise level dashboards
- Community health forums
- Advanced personalized health plans

## 👥 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Contact

Project Lead: Kunal Saha
- Email: [kunalsaha1107@gmail.com]
- Project Link: [https://github.com/Korags05/Healyks](https://github.com/Korags05/Healyks)

## 🏆 Acknowledgements
- Google for Gemini API
- Firebase Authentication
- Android Development Community

## Screenshots

![dashboard](https://github.com/user-attachments/assets/439b74a1-f107-45bf-8b25-2926452f553a) 

![ProfileDashboard](https://github.com/user-attachments/assets/9030ba91-3b70-4d5b-8711-1ad8e4fa8d0e) 

![analyze1](https://github.com/user-attachments/assets/8c57f4b6-af67-4dbf-a878-60deb4130fa4)  

![analyze2](https://github.com/user-attachments/assets/730eb902-a8da-4093-b259-031e9c43e663)

![educationalItems1](https://github.com/user-attachments/assets/d266e028-185e-4f47-afff-072bbcecf8de)

![FirstAid1](https://github.com/user-attachments/assets/198a93d6-5245-4c8a-9b9a-aaee61ab0088)

![FirstAid2](https://github.com/user-attachments/assets/843aacc7-d061-4bfa-b7b7-5e7495ad975c)

![PeriodTracker1](https://github.com/user-attachments/assets/828cd93f-47bb-4110-8051-18c9e9980300)

![PeriodTracker2](https://github.com/user-attachments/assets/ae9bc3a1-bb23-408d-8c9c-c2a6977af106)

![PeriodTracker3](https://github.com/user-attachments/assets/b6ad2e27-d305-4401-9997-4ef88a455327)
