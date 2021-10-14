const imageUpload = document.getElementById("upload-picture")
const descriptionInput = document.getElementById("description-input")
const uploadConfirmButton = document.getElementById("button-confirm")

let picture

function uploadFile() {
    if (!picture || !picture.type.startsWith("image")) {
        console.log("No valid image selected")
        return
    }
    if (!name || name === "") {
        console.log("Not logged in")
        return;
    }
    const description = descriptionInput.value
    if (!description || description === "") {
        console.log("No valid image description")
        return
    }

    let toSend = {
        "username": name,
        "description": description
    };

    const reader = new FileReader();
    reader.onload = function(data) {
        toSend.data = data.target.result
        const base = window.location.origin
        fetch(`${base}/upload`, {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(toSend)
        }).then(async response => {
            const id = await response.text()
            window.location.replace(`${base}/foto/${name}/${id}`)
        }).catch(error => console.error(error))
        console.log(toSend)
    }

    reader.readAsDataURL(picture)
}


function handleDrop(evt) {
    evt.stopPropagation()
    evt.preventDefault()

    const files = evt.dataTransfer.files
    picture = files[0]
    imageUpload.textContent = picture.name
}

function handleDragOver(evt) {
    evt.stopPropagation()
    evt.preventDefault()
    evt.dataTransfer.dropEffect = "copy"
}

imageUpload.addEventListener("dragover", handleDragOver)
imageUpload.addEventListener("drop", handleDrop)
uploadConfirmButton.addEventListener("click", uploadFile)