import Swal from 'sweetalert2';

export const Toast = Swal.mixin({
  toast: true,
  position: 'center-center',
  showConfirmButton: false,
  timer: 1000,
  timerProgressBar: true,
  didOpen: (toast) => {
    toast.addEventListener('mouseenter', Swal.stopTimer);
    toast.addEventListener('mouseleave', Swal.resumeTimer);
  },
});

export const SwalModals = (icon, title, html, btn) => {
  Swal.fire({
    icon: icon,
    title: title,
    html: html,
    showConfirmButton: btn,
    showClass: {
      popup: 'animate__animated animate__fadeInDown',
    },
    hideClass: {
      popup: 'animate__animated animate__fadeOutUp',
    },
    timer: 1500,
  });
};
