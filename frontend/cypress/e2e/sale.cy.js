// const BASE_URL = "https://woochacha.vercel.app";
const BASE_URL = "http://localhost:3000";
const carNumber = "78하5936";

describe('판매', () => {
  describe('사용자 판매 신청', () => {
    before('로그인', () => {
      cy.visit(BASE_URL);

      // 로그인 버튼 클릭
      cy.get('button[aria-label="로그인하기"]').click();
      
      // 로그인 메뉴 클릭
      cy.focused().click();
      cy.url().should('eq', BASE_URL + '/users/login');

      // 로그인 정보 입력
      const email = 'user31@woochacha.com'; 
      cy.get('#email').type(email).as('btn'); 
      cy.wait(500);
      // cy.get('#email').type(email).should('have.value', email);

      const password = 'user311234!'; 
      cy.get('#password') 
        .type(password, { force: true })
        .should('have.value', password);

      // 현재는 배포된 서버에서 로그인 버튼 한 번만 누르면 로그인 실패 모달이 떠서,
      // 두 번 클릭하는 것으로 우선 작성
      cy.wait(500);
      cy.get('.css-11uv7iu > .MuiBox-root > .MuiButton-root').click();
      cy.wait(1000);
      cy.get('.css-11uv7iu > .MuiBox-root > .MuiButton-root').click();
      
      // 메인홈으로 이동 확인
      cy.wait(500);
      cy.url().should('eq', BASE_URL + "/");
    }),

    it('판매 신청', () => {
      // 판매 앱바 클릭
      cy.get('#__next > header > div > div > div.MuiBox-root.css-19pu4hj > li:nth-child(2)').click();

      // 경로 이동 확인
      cy.url().should('eq', BASE_URL + '/product/sale');

      // 차량번호 입력
      cy.get('#carNumber')
      .type(carNumber)
      .should('have.value', carNumber);

      // 차고지 서울 선택
      cy.wait(500);
      cy.get('.MuiSelect-select').click();
      cy.focused().click();

      // 신청하기 버튼 클릭
      cy.get('button.MuiButtonBase-root[type="submit"]').click();

      // 메인홈으로 이동 확인
      cy.wait(500);
      cy.url().should('eq', BASE_URL + "/");
    })
  })

  describe('관리자 판매 승인', () => {
    before('로그인', () => {
      cy.visit(BASE_URL);

      // 로그인 버튼 클릭
      cy.get('button[aria-label="로그인하기"]').click();
      
      // 로그인 메뉴 클릭
      cy.focused().click();
      cy.url().should('eq', BASE_URL + '/users/login');

      // 로그인 정보 입력
      const email = 'woochacha@woorifisa.com'; 
      cy.get('#email').type(email).as('btn'); 
      cy.wait(500);
      // cy.get('#email').type(email).should('have.value', email);

      const password = 'woochacha1234!'; 
      cy.get('#password') 
        .type(password, { force: true })
        .should('have.value', password);

      cy.wait(500);
      cy.get('.css-11uv7iu > .MuiBox-root > .MuiButton-root').click();
      cy.wait(1000);
      cy.get('.css-11uv7iu > .MuiBox-root > .MuiButton-root').click();
    
      // 메인홈으로 이동 확인
      cy.wait(500);
      cy.url().should('eq', BASE_URL + "/");
    }),

    it('판매 신청', () => {
      // 메인홈으로 이동 확인
      cy.wait(500);
      cy.url().should('eq', BASE_URL + "/");

      // 관리자 페에지 앱바 클릭
      cy.wait(1000);
      cy.get('#fade-button').click();

      // 판매 신청 관리 메뉴 클릭
      cy.contains('li', '판매 신청 관리').eq(0).click();

      // 경로 이동 확인
      cy.url().should('eq', BASE_URL + '/admin/sales');

      // 이전에 사용자가 판매를 신청한 carNum의 판매 신청폼 행 찾기
      cy.findTableRowWithText(carNumber).then((matchingRows) => {
        if (matchingRows.length > 0) {
          const firstMatchingRow = matchingRows[0];
          
          // 매물 등록 버튼 클릭
          cy.get(firstMatchingRow).find('td:nth-child(4) button').contains('매물등록').click({force: true});
        } else {
          cy.log('일치하는 행을 찾을 수 없습니다.');
        }
      });
    
      // 점검 정보 승인 페이지로 이동
      cy.wait(2000);
      cy.url().should('eq', BASE_URL + '/admin/sales/approve/62');

      // 이동한 점검 정보 승인 페이지의 차량 번호가 이전에 사용자가 신청한 차량 번호와 일치하는지 확인
      cy.get('.MuiGrid-container > :nth-child(1) > .MuiTypography-root').invoke('text').then((text) => {
        if(text === carNumber) return;
      });

      cy.get('#carDistance').invoke('val').then((distance) => {
        // 가져온 주행 거리에서 50을 더함
        const updatedDistance = parseInt(distance) + 50;
      
        // 수정된 주행 거리를 다시 입력
        cy.get('#carDistance').clear() .type(updatedDistance.toString());
      });

      // "승인 신청" 버튼 클릭
      cy.get('button.MuiButton-containedPrimary[type="submit"]').click();

      // 모달 확인
      cy.get('.MuiDialog-root').should('be.visible');

      // "등록하기" 버튼 클릭
      cy.get('.MuiDialogActions-root > .MuiButtonBase-root').click();

      // 판매 가격 입력
      cy.wait(2000);
      const price = 2000;
      cy.get('#priceVal')
        .type(price)
        .should('have.value', price);

      // "사진 업로드하기" 버튼 클릭
      cy.get('label > .MuiButtonBase-root').click();
      
      const src = 'cypress/image/';
      cy.get('input[type="file"][multiple]')
        .selectFile([src + 'test1.png', src + 'test2.png', 
        src + 'test3.png', src + 'test4.png'], { force: true });

        // cy.get('button.MuiButton-containedPrimary[type="button"]').click();

        cy.contains('button', '등록하기').click();

    })
  })
})

// carNumber 텍스트를 차량번호로 갖는 테이블의 행 찾기
Cypress.Commands.add('findTableRowWithText', (text) => {
  return cy.get('table tr').then((rows) => {
    const matchingRows = [];
    
    return new Cypress.Promise((resolve) => {
      rows.each((index, row) => {
        const secondColumnText = Cypress.$(row).find('td:nth-child(2)').text();
        if (secondColumnText === text) {
          matchingRows.push(row);
        }
        if (index === rows.length - 1) {
          resolve(matchingRows);
        }
      });
    });
  });
});








