import React from 'react'

export default function Preview(scripts) {
    var tempImage = new Image();
    var reader = new FileReader();

    return (
        <div>
            {scripts.map(script => (
                reader.readAsDataURL(script),
                tempImage.src = reader.result,
                <img src={tempImage.src} alt="script"/>
            ))}
        </div>
    )
}