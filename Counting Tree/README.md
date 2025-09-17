```mermaid
classDiagram
    class User {
        +int userId
        +String name
        +String email
        +String password
        +String photo
    }

    class Role {
        +int roleId
        +String name
        +String description
    }

    class Plant {
        +int plantId
        +String mainPhoto
        +Coordinate location
        +Date plantingDate
        +VerificationStatus verificationStatus
        +HealthStatus healthStatus
    }

    class PlantPhoto {
        +int photoId
        +String url
        +Date dateTaken
        +String comment
    }

    class Species {
        +int speciesId
        +String commonName
        +String scientificName
        +String description
        +String requirements
    }

    class Alert {
        +int alertId
        +String type
        +Date creationDate
        +AlertStatus status
    }

    class Zone {
        +int zoneId
        +String name
        +List~Coordinate~ coordinates
    }

    class Comment {
        +int commentId
        +String content
        +DateTime timestamp
    }

    class Log {
        +int actionId
        +String actionType
        +DateTime timestamp
    }

    class Export {
        +ExportFormat format
        +String filters
    }

    class Coordinate {
        +double latitude
        +double longitude
    }

    %% Relaciones
    User --> Role : has
    User --> Plant : registers/verifies
    User --> Alert : creates/resolves
    User --> Comment : author

    Plant --> Species : belongs to
    Plant --> Zone : located in
    Plant --> HealthStatus : has
    Plant --> PlantPhoto : has
    Plant --> Coordinate : has
    Plant --> Alert : has
    Plant --> Comment : relatedPlant

    Alert --> Comment : relatedAlert

    Log --> User : performedBy
    Log --> Plant : relatedPlant
    Log --> Alert : relatedAlert

    Export --> User : generatedBy


```
---

# Modelo UML completo Contando Árboles (Counting Trees) – actualizado

## Clases

### User
- userId : int
- name : String
- email : String
- password : String
- role : Role
- photo : String  # URL o ruta de la foto de perfil

### Role
- roleId : int
- name : String
- description : String

### Plant
- plantId : int
- mainPhoto : String
- location : Coordinate
- plantingDate : Date
- verificationStatus : VerificationStatus
- healthStatus : HealthStatus
- photos : List<PlantPhoto>

### PlantPhoto
- photoId : int
- url : String
- dateTaken : Date
- comment : String
- plant : Plant

### Species
- speciesId : int
- commonName : String
- scientificName : String
- description : String
- requirements : String

### Alert
- alertId : int
- type : String
- creationDate : Date
- status : AlertStatus  # enum: PENDING, IN_PROGRESS, RESOLVED, CANCELLED

### Zone
- zoneId : int
- name : String
- coordinates : List<Coordinate>

### Comment
- commentId : int
- content : String
- timestamp : DateTime
- author : User
- relatedPlant : Plant (optional)
- relatedAlert : Alert (optional)

### Log
- actionId : int
- actionType : String
- timestamp : DateTime
- performedBy : User
- relatedPlant : Plant (optional)
- relatedAlert : Alert (optional)

### Export
- format : ExportFormat
- filters : String
- generatedBy : User

### Coordinate
- latitude : double
- longitude : double

---

## Relaciones (multiplicidades)

- User 1 → 1 Role (has)
- User 1 → 0..* Plant (registers/verifies)
- User 1 → 0..* Alert (creates/resolves)
- User 1 → 0..* Comment (author)

- Plant 1 → 1 Species (belongs to)
- Plant 1 → 1 Zone (located in)
- Plant 1 → 1 HealthStatus (has)
- Plant 0..* → 0..* Alert (has)
- Plant 0..* → 0..* Comment (relatedPlant)
- Plant 1 → 0..* PlantPhoto (has)

- Alert 0..* → 0..* Comment (relatedAlert)

- Log 0..* → 1 User (performedBy)
- Log 0..* → 0..* Plant (relatedPlant)
- Log 0..* → 0..* Alert (relatedAlert)

- Export 1 → 1 User (generatedBy)

---

## Enums

### VerificationStatus
- PENDING
- VERIFIED

### AlertStatus
- PENDING
- IN_PROGRESS
- RESOLVED
- CANCELLED

### ExportFormat
- EXCEL
- CSV
