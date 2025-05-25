# JavaFX Hotel Booking App

A desktop application for managing hotel registrations, room bookings, and customer histories, built with Java 11, JavaFX, and Maven.

---

## 🚀 Features

- **Customer & Admin Accounts**  
  - Register/login as **Customer** or **Hotel Admin**  
  - Role-based dashboards

- **Hotel Management (Admin)**  
  - Register a hotel & add room-types & rooms  
  - View and toggle room availability  
  - “Wait-list” for full rooms

- **Room Booking (Customer)**  
  - Browse **Available Hotels** (alphabetically sorted via BST)  
  - View **Available Rooms** per hotel  
  - Enforce one-booking-per-date-per-customer (Interval Tree)  
  - “Undo Last Booking” (Stack)  
  - Prevent double-bookings across different hotels (Interval Tree)

- **Data Persistence**  
  - JSON files for `customers.json`, `hotelAdmins.json`, `hotels.json`, `waitlists.json`, `reservations.json`  
  - DAOs using Jackson with `jackson-datatype-jsr310` for `LocalDate`

---

## 🗂️ Project Structure
```bash
src/
├── main/
│ ├── java/com/example/
│ │ ├── data/ # DAOs & services(BookingServiceHotelDAO, AuthService, ...)
│ │ ├── model/ # domain models (Customer, Hotel, Room, BookingHistory, ...)
│ │ └── ui/ # JavaFX controllers & FXML
│ └── resources/
│ └── com/example/ui/
│ ├── *.fxml
│ └── styles.css
pom.xml
README.md
admins.json
customers.json
hotels.json
reservations.json
waitlists.json


---

## ⚙️ Prerequisites

- Java 11 SDK  
- Maven 3.6+  
- (Optional) Git for version control

---

## 🛠️ Build & Run

```bash
# Clone or download the repo
git https://github.com/GibranMaulana/Gibook.git
cd Gibook

# Build & launch via Maven
mvn clean javafx:run
