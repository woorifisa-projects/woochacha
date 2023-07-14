    // 버튼 클릭 이벤트 처리
    var userGetBtn = document.getElementById("user-get");
    var userUpdateBtn = document.getElementById("user-update");
    var userDeleteBtn = document.getElementById("user-delete");
    var productFindAllBtn = document.getElementById("product-find-all");
    var productFindOneBtn = document.getElementById("product-find-one");
    var productAddBtn = document.getElementById("product-add");
    var productUpdateBtn = document.getElementById("product-update");
    var productDeleteBtn = document.getElementById("product-delete");
    var inputBox = document.getElementById("input-box");


axios.defaults.withCredentials = true;


userGetBtn.addEventListener("click", function() {
    // inputBox.value = "마이페이지 버튼을 눌렀습니다.";

    axios.get("http://localhost:8080/users").then((res) => {
        console.log(res.data);
        const data = res.data;
        const container = document.getElementById('container');

        const userElement = document.createElement('div');
        userElement.innerHTML = `
            <h3>회원 정보</h3>
            <ul>
                <li><strong>이메일:</strong> ${data.userEmail}</li>
                <li><strong>닉네임:</strong> ${data.nickname}</li>
                <li><strong>성별:</strong> ${data.gender}</li>
            </ul>
        `;

        container.appendChild(userElement);
    })
});

    userUpdateBtn.addEventListener("click", function() {
        inputBox.value = "회원 정보 수정 버튼을 눌렀습니다.";
    });

    userDeleteBtn.addEventListener("click", function() {
        // inputBox.value = "회원 탈퇴 버튼을 눌렀습니다.";

        axios.patch("http://localhost:8080/users/delete")
        .then(()=>{
            console.log("######### delete success");
        })
    });

    productFindAllBtn.addEventListener("click", function() {
        inputBox.value = "전체 상품 보기 버튼을 눌렀습니다.";
        
        axios.get("http://localhost:8080/products").then((res)=>{
            console.log(res.data);
            const data = res.data;
            const container = document.getElementById('container');
            container.textContent = "";
            
            data.forEach(get => {
                const getElement = document.createElement('div');
                getElement.textContent += "제목 : " + get.title;
                getElement.textContent += " / 시간 : " + get.price; 
                getElement.textContent += " / 생성 시간 : " + get.createdAt; 
                getElement.textContent += " / 가격 : " + get.price; 
                getElement.textContent += " / 사진 : ";


                get.productImages.forEach(image => {
                    getElement.textContent += image.productImage + ", ";
                });

                container.appendChild(getElement);
            });
            console.log("######### find all success");
        })
    });

    productFindOneBtn.addEventListener("click", function() {
        // inputBox.value = "상품 상세 정보 보기 버튼을 눌렀습니다.";
        // inputBox.value =  '{"productId" : }';

        const request_data = JSON.parse(inputBox.value);

        axios.get("http://localhost:8080/products/find-one", {
            headers : request_data
        })
        .then((res)=>{
            console.log(res.data);
            const data = res.data;
            const container = document.getElementById('container');
            
            
            const getElement = document.createElement('div');
            container.textContent = "";
            getElement.textContent += "제목 : " + data.title;
            getElement.textContent += " / 시간 : " + data.price; 
            getElement.textContent += " / 생성 시간 : " + data.createdAt; 
            getElement.textContent += " / 가격 : " + data.price; 
            getElement.textContent += " / 사진 : ";
            data.productImages.forEach(image => {
                getElement.textContent += image.productImage + ", ";
            });
            container.appendChild(getElement);
            
            console.log("######### find one success");
        })

    });

    productAddBtn.addEventListener("click", function() {

        const request_data = JSON.parse(inputBox.value);

        axios.post("http://localhost:8080/products/", request_data)
        .then(()=>{
            console.log("######### create success");
        })
    });

    productUpdateBtn.addEventListener("click", function() {
        // inputBox.value = "상품 수정하기 버튼을 눌렀습니다.";
        const request_data = JSON.parse(inputBox.value);

        axios.patch("http://localhost:8080/products/update", request_data)
        .then(()=>{
            console.log("######### update success");
        })
    });
    productDeleteBtn.addEventListener("click", function() {
        // inputBox.value = "상품 삭제하기 버튼을 눌렀습니다.";

        const request_data = JSON.parse(inputBox.value);

        axios.patch("http://localhost:8080/products/delete", request_data)
        .then(()=>{
            console.log("######### delete success");
        })
    });