// Toggle Animation by Class
window.addEventListener('scroll', function () {
    if (window.scrollY > 100) {
        document.querySelector('nav').classList.add('navbar-animate');
    } else {
        document.querySelector('nav').classList.remove('navbar-animate');
    }
});

function parallaxBackground() {
    console.log('parallaxBackground()');
    const scrolled = window.scrollY;
    const bgPosition = `0 ${-(scrolled / 2)}px`;
    document.body.style.backgroundPosition = bgPosition;
}

// 스크롤 이벤트 리스너 등록
window.addEventListener('scroll', parallaxBackground);