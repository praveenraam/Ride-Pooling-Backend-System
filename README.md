# Airport Ride Pooling Backend System
## Overview

This project implements a backend system for an airport ride pooling service, where passengers traveling in similar routes are grouped into shared cabs. The system efficiently assigns passengers to ride groups while respecting seat capacity, luggage constraints, and concurrency requirements.

This backend is designed with production-level architecture using Spring Boot, MySQL, and JPA.

## Features
### Ride Pooling

- Groups passengers traveling between the same pickup and drop locations.
- Automatically assigns passengers to existing ride groups if capacity allows.
- Creates new ride groups when no suitable group exists.
- Ensures optimal utilization of cab capacity.

### Cab Management

- Create and manage cab inventory.

- Assign cabs automatically to ride groups.

- Track cab availability status:

  - AVAILABLE

  - IN_USE

  - OUT_OF_SERVICE

Automatically releases cab when ride completes.

## Ride Lifecycle Management

#### Supports complete ride lifecycle:
```
REQUESTED → ASSIGNED → COMPLETED
```

#### Ride group lifecycle:
```
ACTIVE → FULL → COMPLETED
```

## Dynamic Pricing

- Pricing is calculated dynamically using:
- Base fare
- Luggage surcharge
- Demand-based multiplier
- Pooling discount

This ensures fair and scalable pricing.

## Concurrency Handling

The system is designed to safely handle concurrent ride requests using:
- Transaction management (@Transactional)
- Database row-level locking (PESSIMISTIC_WRITE)
- Prevents seat overbooking and race conditions

Supports high concurrency safely.

## Database Optimization

Indexes are added for fast lookup:

- ride_group(pickup_location, drop_location, status)
- ride_request(ride_group_id, status)

Ensures efficient performance at scale.

## Swagger UI available at:
```
http://localhost:8085/swagger-ui/index.html
```
Allows easy testing of all APIs.

## Technology Stack

Backend Framework:

    Spring Boot

Database:

    MySQL

    Spring Data JPA (Hibernate)

Build Tool:

    Maven

Documentation:

    Swagger (OpenAPI)

Language:

    Java 17+

## Project Architecture

```
controller/
    RideRequestController.java
    CabController.java

service/
    RideRequestService.java
    CabService.java
    PricingService.java

repository/
    RideRequestRepository.java
    RideGroupRepository.java
    CabRepository.java

entity/
    RideRequest.java
    RideGroup.java
    Cab.java

dto/
    RideRequestDto.java
    CabDto.java
```

## Layer Architecture
```
Controller → Service → Repository → Database
```

## How to run the project

1. Clone Repository
```
    git clone https://github.com/praveenraam/Ride-Pooling-Backend-System.git
```
2. Configure MySQL
```
    CREATE DATABASE ridepooling;
```
3. Update application.props

```
spring.application.name=ridepooling
server.port=8085

spring.datasource.url=jdbc:mysql://localhost:3306/ridepooling
spring.datasource.username=root
spring.datasource.password= 

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
```
4. Run the application

## API Endpoints
### Cab APIs
Create Cab

    POST /cab/create

    {
        "cabNumber": "CAB-001",
        "maxSeats": 4,
        "maxLuggage": 4
    }


Get All Cabs

    GET /cab/all

Get Available Cabs

    GET /cab/available

Mark Cab Out of Service

    POST /cab/out-of-service/{id}

Mark Cab Available

    POST /cab/available/{id}

### Ride APIs

Create Ride Request

    POST /ride/request

    {
        "passengerName": "User",
        "pickupLocation": "Airport",
        "dropLocation": "IT Park",
        "luggageCount": 1,
        "maxDetourKm": 5
    }

Cancel Ride

    POST /ride/cancel/{id}

Complete Ride

    POST /ride/complete/{id}

Get Ride Status

    GET /ride/status/{id}

## How Ride Pooling Works

1. User creates ride request.
2. System searches for existing ride group with:

   - Same pickup location
   - Same drop location
   - Available seats
   - Available luggage capacity

3. If found → passenger assigned to group.
4. If not found → new ride group created with available cab.
5. Pricing calculated dynamically.
6. Cab released when ride group completes.
