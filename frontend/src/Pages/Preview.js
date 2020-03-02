import React from 'react'

export default function Preview() {
    var tempImage = new Image();
    var reader = new FileReader();

    return (
        <div>
            {localStorage.getItem("SCRIPT").map(script => (
                reader.readAsDataURL(script),
                tempImage.src = reader.result,
                <img src={tempImage.src} alt="script"/>
            ))}
        </div>
    )
}