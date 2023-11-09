// Define an object with unique information for each doctor
var doctorsInfo = {
    'Dr Max Smith': 'Dr. Max, a graduate of the University of Johannesburg, is a versatile healthcare professional specializing in optometry, gynecology, and orthodontics (dentistry). Their broad skill set enables them to address diverse health issues, offering comprehensive and personalized care. Dr. Max is committed to ensuring optimal health for patients in these specialized fields.',
    'Dr Thabiso Mathews': 'Information about Dr Thabiso Mathews.',
    'Dr Linda Khuzwayo': 'Information about Dr Linda Khuzwayo.'
};

// Function to show doctor information in the modal
function showDoctorInfoModal(doctorName) {
    var individualDoctorInfo = document.getElementById("individualDoctorInfo");
    individualDoctorInfo.innerHTML = "Loading doctor information...";

    // Fetch and display unique information for each doctor
    var doctorInfo = doctorsInfo[doctorName];

    // Update the modal content
    individualDoctorInfo.innerHTML = doctorInfo;
}

// Attach a click event listener to each doctor item
document.querySelectorAll('.doctor-item a').forEach(function (doctorLink) {
    doctorLink.addEventListener('click', function (event) {
        var doctorName = this.getAttribute('data-doctor-name');
        showDoctorInfoModal(doctorName);
    });
});