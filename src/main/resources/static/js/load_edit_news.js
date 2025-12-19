import * as builder from "./news_builder.js";

let id=document.getElementById("news-id").value;
document.getElementById("get-edit-news").addEventListener("click", ()=>{
    let news = builder.fetchNewsById(id);
    builder.buildNewsEditor(news);
    let btnarr = document.getElementsByClassName("del-image");
    for(let i=0; i<btnarr.length; i++) {
        btnarr[i].addEventListener("click", ()=>{
            btnarr[i].previousElementSibling.previousElementSibling
        })
    }
});