let dragSrcEl = null;

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
    try {
        if (window.scrollY > 500) {
            document.querySelector('.scroll-to-top').classList.add('scroll-to-top-show');
        }
        if (window.scrollY < 500) {
            document.querySelector('.scroll-to-top').classList.remove('scroll-to-top-show');
        }
    } catch (e) {
        // console.log(e);
    }
});


function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}


// Drag and Drop
function handleDragStart(e) {
    /* global dragSrcEl */
    console.log(1)
    dragSrcEl = null;
    this.style.opacity = '0.4';
    dragSrcEl = this;
    e.dataTransfer.effectAllowed = 'move';
    e.dataTransfer.setData('text/html', this.innerHTML);
    console.log(e);
}

function handleDragEnd(e) {
    e.target.style.opacity = '1';
    try {
        let items = e.target.closest('.question-list').querySelectorAll('.question-item');
        items.forEach(function (item) {
            item.classList.remove('over');
        });
    } catch (e) {
        console.log(e);
    }
    setQNumWhenDrag(e)
}

function handleDragOver(e) {
    if (e.preventDefault) {
        e.preventDefault();
    }
    return false;
}

function handleDragEnter(e) {
    e.target.classList.add('over');
}

function handleDragLeave(e) {
    e.target.classList.remove('over');
}

function handleDrop(e) {
    e.stopPropagation();
    if (dragSrcEl !== this) {
        try {
            // 복원할 내용을 저장
            let dragSrcContent = dragSrcEl.querySelector('textarea').value;
            // 드롭 대상의 내용을 저장
            // 내용 교환
            dragSrcEl.querySelector('textarea').value = e.querySelector('textarea').value;
            e.querySelector('textarea').value = dragSrcContent;
        } catch (e) {
            console.log(e);
        }
    }
    return false;
}

function setQNumWhenDrag(e, location) {
    // todo sidebar 인지 question-list 인지 구분해서 처리 지금은 양쪽 다 되어있음
    try {
        let questionList = e.target.closest('.question-list');
        let questionNumbering = questionList.querySelectorAll('.item-numbering');
        questionNumbering.forEach(function (item, index) {
            item.innerHTML = `${index + 1}`;
        });
    } catch (e) {
        console.log(e);
    }

    try {
        let questionList = e.target.closest('.sidebar');
        let questionNumbering = questionList.querySelectorAll('.item-numbering');
        questionNumbering.forEach(function (item, index) {
            item.innerHTML = `Q${index + 1}`;
        });
    } catch (e) {
        console.log(e);
    }
}

function setQNumWhenAddOnSideBar(parentUlEl) {
    // todo sidebar 인지 question-list 인지 구분해서 처리 지금은 양쪽 다 되어있음
    parentUlEl.querySelectorAll('.item-numbering').forEach(function (item, index) {
                item.innerHTML = `Q${index}`;
        }
    );
}

function setQNumWhenDeleteOnSideBar(parentUlEl) {
    parentUlEl.querySelectorAll('.item-numbering').forEach(function (item, index) {
            item.innerHTML = `Q${index}`;
        }
    );
}


function addDragAndDropEvent(e) {
    console.log("add event")
    e.addEventListener('dragstart', handleDragStart);
    e.addEventListener('dragover', handleDragOver);
    e.addEventListener('dragenter', handleDragEnter);
    e.addEventListener('dragleave', handleDragLeave);
    e.addEventListener('dragend', handleDragEnd);
    e.addEventListener('drop', handleDrop);
}

function htmlToElement(html) {
    let template = document.createElement('div');
    template.innerHTML = html;
    return template.children[0];
}