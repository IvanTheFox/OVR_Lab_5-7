async function fetchNewsById(id) {
    try {
        const response = await fetch(`http://localhost:8089/newsinfo/?id=${id}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); 
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function buildNewsEditor(news){
    let article = document.getElementsByClassName("article-content")[0];
    let imagesHTML="";

    news.pictures.forEach(picPath => {
        imagesHTML+='<div class="news-image">'+
        `<img alt="fto" src=${picPath}><br>`+
        '<button class="del-image">X</button>'+
        '</div>';
    })
    article.innerHTML=`<h2><input id="article-title" required>${news.title}</h2>
        <textarea id="article-text" rows="15" maxlength="3000" required>${news.text}</textarea><br>
        ${imagesHTML}
        <input type="file" name="files" id="article-images" multiple required>`;
}

document.getElementById("get-edit-news").addEventListener("click", ()=>{
    let id=document.getElementById("news-id").value;
    let news = fetchNewsById(id);
    buildNewsEditor(news);
    let btnarr = document.getElementsByClassName("del-image");
    for(let i=0; i<btnarr.length; i++) {
        btnarr[i].addEventListener("click", ()=>{
            btnarr[i].parentElement.innerHTML="";
            updateUploadSizeCheck(news.pictures.length);
        });
    }
    updateUploadSizeCheck(news.pictures.length);
});

function updateUploadSizeCheck(loadedAmount){
    let upload = document.getElementById("article-images");
    upload.addEventListener("change",(event)=>{
        if(event.target.files.length>5-loadedAmount) {
            document.getElementById("error-msg").innerHTML="Внимание, количество предоставленных файлов больше максимального(5)!";
        }
    });
}