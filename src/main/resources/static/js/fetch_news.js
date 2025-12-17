async function fetchGreeting() {
    try {
        // The URL should match your Spring Boot endpoint
        const response = await fetch('http://localhost:8080/newsinfo/1'); 

        // Check if the request was successful
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Parse the response body as JSON
        const data = await response.json(); 

        console.log(data.message); // Output: Hello from Spring Boot!
        // You can now use this data to update your HTML/UI

    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

// Call the function
fetchGreeting();