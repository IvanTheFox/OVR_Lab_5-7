document.getElementById("change-edit-news").addEventListener("click",()=>{
    updateNews();
});

async function updateNews() {
    const fileInput = document.getElementById('article-images');
    const formData = new FormData();
    for (const file of fileInput.files) {
        formData.append('files', file);
    }

    formData.append("title",document.getElementById("article-title").value);
    formData.append("text",document.getElementById("article-text").value);

    let oldFilesArr = document.getElementsByClassName("news-image");
    let attributes = new Array();
    for (let i=0; i<oldFilesArr.length; i++){
        attributes.push(oldFilesArr[i].getAttribute("src"));
    }
    formData.append("existingFiles",attributes);

    formData.append("newsId", document.getElementById("news-id").value);

    try {
        const response = await fetch('/editnews', {
            method: 'POST',
            body: formData 
        });

        if (response.ok) {
            const result = await response.text();
            console.log('File uploaded successfully:', result);
            document.getElementById("response-text").innerHTML=result;
        } else {
            console.error('File upload failed with status:', response.status);
            alert('File upload failed.');
        }
    } catch (error) {
        console.error('Error during file upload:', error);
        alert('Error during file upload.');
    }
}