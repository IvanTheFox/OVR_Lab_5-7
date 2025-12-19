document.getElementById("make-new-news").addEventListener("click",()=>{
    createNews();
});

async function createNews() {
    const fileInput = document.getElementById('fileInput');
    const files = fileInput.files;

    const formData = new FormData();
    formData.append('files', files);

    formData.append("title",document.getElementById("article-title").value);
    formData.append("text",document.getElementById("article-text").value);

    let oldFilesArr = document.getElementsByClassName("news-image");
    let attributes = new Array();
    oldFilesArr.forEach(file => {
        attributes.push(file.getAttribute("src"));
    });
    formData.append("existingFiles",attributes);

    try {
        const response = await fetch('/newnews', {
            method: 'POST',
            body: formData 
        });

        if (response.ok) {
            const result = await response.text();
            console.log('File uploaded successfully:', result);
        } else {
            console.error('File upload failed with status:', response.status);
            alert('File upload failed.');
        }
    } catch (error) {
        console.error('Error during file upload:', error);
        alert('Error during file upload.');
    }
}