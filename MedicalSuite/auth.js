// auth.js

// Initialize Firebase
var firebaseConfig = {
    // Your Firebase config here
    apiKey: "AIzaSyBVmFA1iK5MSKLIhf3ACo6E0meUeRbPCgs",
    authDomain: "medicalsuite-99e48.firebaseapp.com",
    projectId: "medicalsuite-99e48",
    storageBucket: "medicalsuite-99e48.appspot.com",
    messagingSenderId: "679686285511",
    appId: "1:679686285511:web:be7e727760a15a61a9f6e2"
};

firebase.initializeApp(firebaseConfig);



// Function to check if the user is authenticated
function checkAuth() {
  firebase.auth().onAuthStateChanged(function (user) {
    if (!user) {
      // Redirect to login page if not authenticated
      window.location.href = '/patient/login.html';
    } else {
      // If authenticated, continue with page-specific logic or display content
      handleAuthenticatedUser(user);
    }
  });
}

// Additional function for page-specific logic after authentication
function handleAuthenticatedUser(user) {
  // You can perform page-specific actions here
  console.log('User authenticated:', user.uid);
}

// Logout function
function logout() {
  firebase.auth().signOut().then(function() {
    // Sign-out successful.
    window.location.href = "/patient/login.html"; // Redirect to the login page
  }).catch(function(error) {
    // An error happened.
    console.error("Logout error:", error);
  });
}
