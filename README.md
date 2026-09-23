## Off-Campus Student Housing Directory (OCSHD) System

**CS350 Team Project — ByteClub, Sem II 2026**

### Overview
OCSHD is a Java application that helps students find reliable, affordable, off-campus housing near their campus. It replaces fragmented, unverified sources (noticeboards, word-of-mouth, social media groups) with a centralized directory of boarding houses that can be searched by price, distance, and availability.

### Problem
Students face outdated pricing/availability info, no structured way to filter by budget or proximity to campus, and exposure to scams or unreliable landlord contacts due to the lack of a centralized system.

### Solution
A modular, OOP-based Java application that models properties, landlords, locations, and amenities as distinct classes, with search/filter logic based on distance, budget, and status (`AVAILABLE`, `FULL`, `UNDER_MAINTENANCE`).

### Target Users
- **Students** — search and filter listings by budget, distance, and amenities
- **Landlords** — register properties, update pricing, toggle availability
- **Admins** — verify listings and manage user accounts

### Objectives
1. Model housing domain entities using OOP principles (encapsulation, inheritance, polymorphism, abstraction)
2. Implement search/filtering by distance, price, and amenities
3. Enable real-time status management per property
4. Maintain verified landlord contact directory integrity

### MVP
- Core data model: `BoardingHouse`, `Landlord`, `Location`, `Amenity`, `HousingDirectory`
- Add a new property listing (contact details, distance from campus)
- Search housing by max distance and budget
- Update property occupancy status
- Input validation (e.g. rejecting negative prices, invalid contact numbers)

### Initial Scope
In scope: core Java OOP model, in-memory or JSON/file-based storage, CLI or basic JavaFX UI, input validation.
Deferred: full web/mobile app, persistent multi-user database, payments/bookings, live GPS/maps integration, landlord identity verification (KYC).

### Team
| Name | Student Number |
|---|---|
| Leonard K. M. Zimba | 23126567 |
| Widon Chirwa | 23147068 |
| Willard Kalaba Bob | 24167830 |
| Mwamba Chilufya| 18123318 |
