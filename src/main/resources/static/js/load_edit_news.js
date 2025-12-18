import * as builder from "./news_builder.js";

let id=document.getElementById("news-id").value;
document.getElementById("get-edit-news").addEventListener("click", ()=>{
    let news = builder.fetchNewsById(id);
    builder.buildNewsEditor(news);
});