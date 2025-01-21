console.log("Audio Client script loaded.");

// Elements
const audioListElement = document.getElementById("audio-list");
const audioElement = document.getElementById("audio");
const currentTitleElement = document.getElementById("current-title");
const ytDlpStatusElement = document.getElementById("yt-dlp-status");
const ytDlpStatusLabelElement = document.getElementById("yt-dlp-status-label");
const downloadListElement = document.getElementById("download-list");
const dialogElement = document.getElementById("dialog");
const downloadContainerElement = document.getElementById("download-container");

// Variables
const downloadList = [];

// Events
function loadMusicDirectory() {
    fetch("/api/dashboard/load/music")
        .then(res => res.json())
        .then(res => {
            audioListElement.innerHTML = "";
            res.audioList.forEach(e => {
                const div = document.createElement("div");
                div.className = "item";
                div.textContent = e.name;
                div.onclick = () => loadAudioToPlayer(div, e);
                audioListElement.append(div);
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

function downloadAudio(url) {
    if (!url || url.trim() === "") {
        return;
    }

    const li = document.createElement("li");
    li.textContent = url;
    downloadListElement.append(li);
    downloadList.push({ li, url });

    if (downloadList.length === 1) {
        download({ li, url });
    }

    dialogElement.close();
}

function download({ li, url }) {
    li.classList.add("progress");
    fetch(`/api/downloader?url=${url}`, { method: "POST" })
        .then(res => {
            li.classList.remove("progress")
            li.classList.add(res.ok ? "complete" : "failed")
            downloadList.shift();
            loadMusicDirectory();

            if (downloadList.length === 1) {
                download(downloadList[0]);
            }
        });
}

function toggleDownloadList() {
    downloadContainerElement.classList.toggle("hidden");
}

// Run on start
document.addEventListener("DOMContentLoaded", () => checkForYtDlp());
