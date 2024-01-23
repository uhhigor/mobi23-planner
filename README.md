Application is using Firebase services.

Authentication:
provider - Email/Password

Firestore Database:

collection "users"
  - document {user uid}
      email "example@example.com" (string)
      name "example name" (string)
      uid "1eHUsXkINHeSFKRXKzlekBzNSF22" (string)
      - collection "tasks"
        - document {unique task id}
            dateEnd "dd-MM-YYYY" (string)
            description "Example description" (string)
            done false (boolean)
            group "Example group" (string)
            stepGoal 5 (number)
            stepLengthMinutes 30 (number)
            stepsDone 0 (number)
            title "Example title"
  
