// Toggle Animation by Class
window.addEventListener('scroll', function () {
    // Navbar Animation
    if (window.scrollY > 150) {
        document.querySelector('nav').classList.add('navbar-animate');
    } else {
        document.querySelector('nav').classList.remove('navbar-animate');
    }

    // Parallax Effect
    const scrolled = window.scrollY;
    const bgPosition = `0 ${-(scrolled / 2)}px`;
    document.body.style.backgroundPosition = bgPosition;

    // Scroll to Top Button
    try{
        if (window.scrollY > 500) {
            document.querySelector('.scroll-to-top').classList.add('scroll-to-top-show');
        }
        if(window.scrollY < 500) {
            document.querySelector('.scroll-to-top').classList.remove('scroll-to-top-show');
        }
    }catch(e){
        // console.log(e);
    }
});


function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}