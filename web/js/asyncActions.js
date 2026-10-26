/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("submit", async function (event) {
    const form = event.target;

    if (!form.matches(".async-form")) {
        return;
    }

    event.preventDefault();

    const message = form.querySelector(".action-message");
    const button = form.querySelector('button[type="submit"]');

    try {
        button.disabled = true;
        message.textContent = "Processing...";

        const response = await fetch(form.getAttribute("action"), {
            method: "POST",
            body: new URLSearchParams(new FormData(form))
        });

        const result = await response.text();

        if (!response.ok) {
            console.error("Request failed:", response.status, result);

            throw new Error(
                    response.status === 400
                    ? "The server rejected the request. Check the submitted parameters."
                    : "The operation failed. HTTP status: " + response.status
                    );
        }

        message.textContent = result;

    } catch (error) {
        message.textContent =
                error.message || "An unexpected error occurred.";
    } finally {
        button.disabled = false;
    }
});