const loginButton = document.getElementById("login-button")
const loginConfirmButton = document.getElementById("login-confirm")

const loginBox = document.getElementById("login-box")
const overlay = document.getElementById("content-overlay")
const loginName = document.getElementById("login-name")

let loginVisible = false
let name = ""

function setLoginButtonText() {
    name = document.cookie.split("; ")
        .map(c => c.split("="))
        .filter(c => c[0] === "username")[0][1]
    if (name && name !== "")
        loginButton.textContent = name
}

function switchLoginBox() {
    if (loginVisible) {
        loginBox.style.display = "none"
        overlay.style.display = "none"
        loginVisible = false
    } else {
        loginBox.style.display = "flex"
        overlay.style.display = "block"
        loginVisible = true
    }
}

function login() {
    const newName = loginName.value
    if (newName && newName !== "") {
        document.cookie = `username=${newName}`
        setLoginButtonText()
        console.log(`Logged in as: ${newName}`)
        switchLoginBox()
        loginName.value = ""
    }
}

loginButton.addEventListener("click", switchLoginBox)
overlay.addEventListener("click", switchLoginBox)
loginConfirmButton.addEventListener("click", login)

setLoginButtonText()