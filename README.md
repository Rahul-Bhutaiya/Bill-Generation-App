# 🧾 Bill Generation System

A backend-driven order management system designed for secure, scalable, and automated billing operations with real-time communication and stock tracking.

## 🚀 Features

- **Admin Functions**
  - Add products under specific categories
  - Receive SMS alerts when stock reaches threshold
  - Get daily email reports of orders and stock status

- **User Interactions**
  - Place orders with automatic stock update
  - Receive WhatsApp notifications on order success or failure
  - Paginated product listing (5 items per page)

- **System Mechanics**
  - Applied **Atomicity** to ensure reliable order placement
  - Enforced **Database Normalization** for maintainability
  - Twilio integration for WhatsApp, SMS notifications
  - Spring Boot backend with secure configuration management

## 🛠️ Tech Stack

- **Backend:** Java + Spring Boot  
- **Database:** MySQL
- **Notifications:** Twilio (SMS & WhatsApp)  
- **Email Reports:** JavaMail
- **Security & Config:** Environment-specific setups with `@ConfigurationProperties`  

## 📈 Highlights

- Real-world debugging with property injection logging  
- Sandbox-aware WhatsApp templates via Twilio  
- Clean pagination and threshold-based stock alerts  
- Scalable and maintainable backend architecture

---
