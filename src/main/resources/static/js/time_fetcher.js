async function getServerTime() {
    try {
        const response = await fetch('http://localhost:8080/getservertime'); 
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); 
        console.log(data.message);
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}
let timer = setInterval(()=>{
    let date = new Date(1000*getServerTime());
    document.getElementById("server-time").innerHTML = date.toLocaleString();
}, 1000);