console.log("Audio Client script loaded.");

// Elements
const dashboardElement = document.getElementById("dashboard");
const audioElement = document.getElementById("audio");
const currentTitleElement = document.getElementById("current-title");
const ytDlpStatusElement = document.getElementById("yt-dlp-status");
const ytDlpStatusLabelElement = document.getElementById("yt-dlp-status-label");

// Events
function loadMusicDirectory() {
    fetch("/api/dashboard/load/music")
        .then(res => res.json())
        .then(res => {
            dashboardElement.innerHTML = "";
            res.audioList.forEach(e => {
                const div = document.createElement("div");
                div.className = "item";
                div.textContent = e.name;
                div.onclick = () => loadAudioToPlayer(div, e);
                dashboardElement.append(div);
            });
        });
}

function loadAudioToPlayer(element, e) {
    audioElement.src = `/api/dashboard/load?path=${encodeURIComponent(e.path)}`;
    currentTitleElement.textContent = e.name;
    element.classList.add("active");
    audioElement.play();
}

function checkForYtDlp() {
    fetch("/api/downloader/check")
        .then(res => res.text())
        .then(res => {
            if (res === "true") {
                ytDlpStatusElement.style.background = "green";
                ytDlpStatusLabelElement.textContent = "yt-dlp found";
            } else {
                ytDlpStatusLabelElement.textContent = "Downloading yt-dlp...";
                downloadYtDlp();
            }
        });
}

function downloadYtDlp() {
    fetch("/api/downloader", { method: "POST" })
        .then(res => {
            if (res.ok) {
                ytDlpStatusElement.style.background = "green";
                ytDlpStatusLabelElement.textContent = "yt-dlp found";
            } else {
                ytDlpStatusElement.style.background = "red";
                ytDlpStatusLabelElement.textContent = "yt-dlp error";
            }
        });
}

// Run on start
document.addEventListener("DOMContentLoaded", () => checkForYtDlp());
