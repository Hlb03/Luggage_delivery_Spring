function checkForm() {
    const password = document.getElementById('pas1');
    const vpassword = document.getElementById('pas2');
    const checkBox = document.getElementById('checkBox');

    document.getElementById("registrateBtn").disabled = password.value.length === 0 ||
        password.value !== vpassword.value || !checkBox.checked;
}