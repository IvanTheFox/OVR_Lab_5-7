async function getServerTime() {
    try {
        const response = await fetch('http://localhost:8089/getservertime'); 
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.text(); 
        return data;
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

let timer = setInterval(async ()=>{
    await getServerTime().then( result => {
        let date = new Date(Number(result));
        document.getElementById("server-time").innerHTML = date.toLocaleString();
    });
}, 1000);