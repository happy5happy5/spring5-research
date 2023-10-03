let dragSrcEl = null;
let questionCreateAction = false;
// Toggle Animation control

// .main-content-container.hide{
//     /*top: 2em;*/
//     padding-left: 1em;
//     margin-left: 0;
// }

// .sidebar-container.hide{
//     left: -21em;
// }

// .sidebar-toggle.hide{
//     top: 2em;
//     left: -15em;
// }
function toggleAnimation(e) {
    document.querySelector('#layout-content .main-content-container').classList.toggle('hide');
    document.querySelector('#layout-content .sidebar-container').classList.toggle('hide');
    document.querySelector('#layout-content .sidebar-toggle').classList.toggle('hide');
}



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

// text area auto resize
function autoResizeTextArea(e) {
    e.style.height = 'auto';
    e.style.height = e.scrollHeight + 'px';
}

function handleTextAreaInput(e, str){
    autoResizeTextArea(e)
    if(str === 'Q'){
        let currentNum = document.querySelector('#layout-content #question-number span').textContent;
        let sidebarList = document.querySelector('.sidebar').children;
        let sidebarEl = sidebarList[currentNum];
        let saveData = sidebarEl.querySelector('.item-title').textContent = e.value;
    }
}

// onclick sidebar item

function handleSidebarItemClick(e) {
    // console.log("[SIDEBAR] click",e)
    const currentNum = document.querySelector('#layout-content #question-number span').textContent;
    const clickedNum = e.querySelector('.item-numbering').textContent.split('Q')[1];
    if(currentNum === clickedNum){
        return;
    }
    // console.log("이전 화면 번호 : ",currentNum,"현재 화면 번호 : ",clickedNum);
    const sidebarList = document.querySelector('.sidebar').children;
    saveContentAction(sidebarList,currentNum);
    loadContentAction(sidebarList,clickedNum);
}

function saveContentAction(sidebarList,currentNum){
    let mainContentContainer = document.querySelector('#layout-content .main-content-container');
    let mainContentContainerHTML = mainContentContainer.innerHTML;
    let sidebarEl = sidebarList[currentNum];
    let textareas =[];
    mainContentContainer.querySelectorAll('textarea').forEach(function (item){
        textareas.push(item.value);
    });
    textareas = JSON.stringify(textareas);
    let saveData = sidebarEl.querySelector('.saveData')
    let saveHTML = sidebarEl.querySelector('.saveHTML')
    saveData.value = textareas;
    saveHTML.value = mainContentContainerHTML;
}
function loadContentAction(sidebarList,clickedNum){
    let sidebarEl = sidebarList[clickedNum];
    let saveData = sidebarEl.querySelector('.saveData')
    let saveHTML = sidebarEl.querySelector('.saveHTML')
    // console.log(saveData.value)
    // console.log(saveHTML.value)
    let textareas = JSON.parse(saveData.value);
    let mainContentContainer = document.querySelector('#layout-content .main-content-container');
    mainContentContainer.innerHTML = saveHTML.value;
    if(clickedNum !== '0'){
        let questionList = mainContentContainer.querySelector('.question-list')
        questionList.querySelectorAll('.question-item').forEach(function (item){
            addDragAndDropEvent(item,'Q');
        });
    }

    let mainContentContainerTextareas = mainContentContainer.querySelectorAll('textarea');
    mainContentContainerTextareas.forEach(function (item,index){
        item.value = textareas[index];
    });
    document.querySelector('#layout-content #question-number span').textContent = clickedNum;
}

function stopPropagation(event) {
    event.stopPropagation();
}

// Drag and Drop
function handleDragStart(e) {
    console.log(e)
    /* global dragSrcEl */
    dragSrcEl = null;
    this.style.opacity = '0.4';
    dragSrcEl = this;
    e.dataTransfer.effectAllowed = 'move';
    e.dataTransfer.setData('text/html', this.innerHTML);
}

function handleDragEnd(e) {
    e.target.style.opacity = '1';
    // main-content-container
    try {
        let items = e.target.closest('.question-list').querySelectorAll('.question-item');
        items.forEach(function (item) {
            item.classList.remove('over');
        });
    } catch (e) {
        // console.log(e);
    }

//     sidebar
    try {
        let items = e.target.closest('.sidebar').querySelectorAll('.sidebar-item');
        items.forEach(function (item) {
            item.classList.remove('over');
        })
    }
    catch (e) {
        // console.log(e);
    }
    saveContentAction(document.querySelector('.sidebar').children,document.querySelector('#layout-content #question-number span').textContent);
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
        // main-content-container
        // 복원할 내용을 저장
        let dragSrcContent = dragSrcEl.querySelector('textarea').value;

        // 드롭 대상의 내용을 저장
        // 내용 교환
        dragSrcEl.querySelector('textarea').value = e.target.querySelector('textarea').value;
        e.target.querySelector('textarea').value = dragSrcContent;
    }
    return false;
}

function handleDropForSidebar(e) {
    e.stopPropagation();
        // sidebar
    if (dragSrcEl !== this) {

        let saveData1 = dragSrcEl.querySelector('.saveData').value
        let saveData2 = e.target.querySelector('.saveData').value
        let saveHTML1 = dragSrcEl.querySelector('.saveHTML').value
        let saveHTML2 = e.target.querySelector('.saveHTML').value
        let saveType1 = dragSrcEl.querySelector('.saveType').value
        let saveType2 = e.target.querySelector('.saveType').value

        let temp = dragSrcEl.innerHTML;
        dragSrcEl.innerHTML = e.target.innerHTML;
        e.target.innerHTML = temp;

        dragSrcEl.querySelector('.saveData').value = saveData2
        dragSrcEl.querySelector('.saveHTML').value = saveHTML2
        dragSrcEl.querySelector('.saveType').value = saveType2
        e.target.querySelector('.saveData').value = saveData1
        e.target.querySelector('.saveHTML').value = saveHTML1
        e.target.querySelector('.saveType').value = saveType1

        let parentUlEl = e.target.closest('ul');
        setQNumWhenAddOnSideBar(parentUlEl);
        let sidebarList = document.querySelector('.sidebar').children;
        let currentNum = document.querySelector('#layout-content #question-number span').textContent;
        loadContentAction(sidebarList,currentNum);
    }
    return false;
}

// sidebar

function setQNumWhenAddOnSideBar(parentUlEl) {
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

function addDragAndDropEvent(e,str) {
    // console.log("[index.js] add drag event")
    e.addEventListener('dragstart', handleDragStart);
    e.addEventListener('dragover', handleDragOver);
    e.addEventListener('dragenter', handleDragEnter);
    e.addEventListener('dragleave', handleDragLeave);
    e.addEventListener('dragend', handleDragEnd);
    if(str === 'Q'){
        e.addEventListener('drop', handleDrop);
    }else if(str === 'S'){
        e.addEventListener('drop', handleDropForSidebar);
    }
}

function htmlToElement(html) {
    let template = document.createElement('div');
    template.innerHTML = html;
    return template.children[0];
}


// Add Question in main content

function setQNumWhenDeleteOnMainContent(questionList) {
    let questionNumbering = questionList.querySelectorAll('.item-numbering');
    questionNumbering.forEach(function (item, index) {
        item.innerHTML = `${index + 1}`;
    });
}

function setQNumWhenAddOnMainContent(e) {
    let questionList = e.parentElement.querySelector('.question-list');
    let questionNumbering = questionList.querySelectorAll('.item-numbering');
    questionNumbering.forEach(function (item, index) {
        item.innerHTML = `${index + 1}`;
    });
}

function handleQuestionAddButtonOnMainContent(e){
    let questionList = e.parentElement.querySelector('.question-list');
    // let questionItem = questionList.querySelector('.question-item');
    if(questionList.children.length !== 0){
        if(questionList.children.length >4){
            alert("보기는 최대 5개까지 추가할 수 있습니다.")
        }
    }
//     새로 만들기
    let newQuestionHTML = `
                        <li class="question-item d-flex col justify-content-evenly" draggable="true">
                            <div class="item-numbering col-1">1</div>
                            <textarea class="col-8" placeholder="보기를 입력하세요" oninput="handleTextAreaInput(this)"></textarea>
                            <button class="question-item-delete col-1 btn btn-toolbar shadow-none bg-transparent" onclick="handleQuestionItemDeleteButtonClick(this)">
                                <span>del</span>
                            </button>
                        </li>
    `;
    let newQuestion = htmlToElement(newQuestionHTML);
    questionList.appendChild(newQuestion);
    addDragAndDropEvent(newQuestion,'Q');
    setQNumWhenAddOnMainContent(e);

}

function handleQuestionItemDeleteButtonClick(e){
    let questionItem = e.parentElement;
    let questionList = questionItem.parentElement;
    questionList.removeChild(questionItem);
    setQNumWhenDeleteOnMainContent(questionList);
}