window.onload = function() {
    const logo = document.querySelector('.topbar-wrapper img');
    if (logo) {
        logo.src = '/swagger-ui/custom-logo.png';
        logo.alt = 'Movie Library';
    }
};