import * as builder from "./news_builder.js";

for (let i=0, j=-1; i<5; i++) {
    let news = builder.fetchNewsByPrev(j);
    j = builder.buildNews(news);
}
document.getElementById("loadmore-btn").addEventListener("click", ()=>{
    for (let i=0; i<5; i++) {
        let news = builder.fetchNewsByPrev(j);
        j = builder.buildNews(news);
    }
});