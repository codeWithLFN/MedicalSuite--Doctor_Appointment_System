
        // Include your actual Firebase configuration (the config file is on the firebase folder)
        var firebaseConfig = {
            apiKey: "AIzaSyBVmFA1iK5MSKLIhf3ACo6E0meUeRbPCgs",
            authDomain: "medicalsuite-99e48.firebaseapp.com",
            projectId: "medicalsuite-99e48",
            storageBucket: "medicalsuite-99e48.appspot.com",
            messagingSenderId: "679686285511",
            appId: "1:679686285511:web:be7e727760a15a61a9f6e2"
        };
        firebase.initializeApp(firebaseConfig);

        // Get a reference to the Firestore database
        var db = firebase.firestore();

        // Get a reference to the Firebase Auth service
        var auth = firebase.auth();

        // Add an event listener to the registration form
        document.getElementById("register-form").addEventListener("submit", function(event) {
            event.preventDefault();

            // Get the form data
            var name = document.getElementById("name").value;
            var surname = document.getElementById("surname").value;
            var phoneNumber = document.getElementById("phoneNumber").value;
            var email = document.getElementById("email").value;
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;

            // Validate the form data
            if (name === "" || surname === "" || phoneNumber === "" || email === "" || password === "" || confirmPassword === "") {
                alert("Please fill in all fields.");
                return;
            }
            if (password !== confirmPassword) {
                alert("Passwords do not match.");
                return;
            }

            // Create a new user account with Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
      .then(function(userCredential) {
        // Save the user data to Firestore
        var uid = userCredential.user.uid; // Get the UID
        currentUserUID = uid; // Update the currentUserUID variable

        db.collection("patients").doc(uid).set({
          name: name,
          surname: surname,
          phoneNumber: phoneNumber,
          email: email,
          password: password
        })
        .then(function() {
          alert("Registration successful!");
          window.location.href = "/patient/login.html"; // Redirect to login page
        })
        .catch(function(error) {
          alert("Error saving user data: " + error.message);
        });
      })
      .catch(function(error) {
        alert("Error creating user account: " + error.message);
      });
    })
