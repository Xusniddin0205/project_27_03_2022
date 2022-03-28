Vue.use(window['vue-toastify'].default);
Vue.use(window.vuelidate.default);
const {required, minLength} = window.validators;

var app = new Vue({

    el: '#app',
    data() {
        return {

            size: 20,
            page: 0,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            //search
            searchId: '',
            searchNum: '',
            searchTextNumber: '',
            searchText: '',
            searchType: '',
            //Save questions
            error: {
                number: {has: true, success: false, msg: "Iltimos nomerni kiriting"},
                textNumber: {has: true, succes: false, msg: "Iltimos malumot kiriting"},
                text: {has: true, succes: false, msg: "Iltimos malumot kiriting"},
                type: {has: true, succes: false, msg: "Malumot tanlanmagan"},

            },
            question: {num: '', textNumber: '', text: '', type: ''},
            types: [
                {type: "SHORT", name: "Qisqa matn"},
                {type: "CHECKBOX", name: "Ko'p javobli"},
                {type: "SELECT", name: "Bitta javobli"}
            ],
            formId: null,
            data: null,
            visible: false,
            questions: null
        }

    },
    validations: {

        question: {
            num: {
                required,
                minLength: minLength(1)
            },
            textNumber: {
                required,
                minLength: minLength(1)
            },
            text: {
                required,
                minLength: minLength(1)
            },
            type: {
                required,
                minLength: minLength(1)
            },
        },

    },
    mounted() {
        this.$vToastify.success('easy-peasy');
        axios.post("/admin/quiz/api/question/search",
            {
                id: this.searchId,
                num: this.searchNum,
                textNumber: this.searchTextNumber,
                text: this.searchText,
                type: this.searchType
            }).then(response => {
            this.currentPage = response.data.number + 1
            this.questions = response.data.content
            this.totalPages = response.data.totalPages
            this.totalElements = response.data.totalElements
        });

         console.log(this.questions);
    },
    methods: {
        deleteQuestion(id){

            swal({
                title: "O'chirilsinmi?",
                text: "Ma'lumot to'liq o'chiriladi",
                icon: "warning",
                //buttons: true,
                dangerMode: true,
                buttons: ["Yo'q", "Xa"],
            }) .then((willDelete) => {
                if (willDelete) {
                    axios.get("/admin/quiz/api/question/delete/"+id)
                        .then(response =>{
                            this.getData();
                            console.log(response);
                        } );

                    swal("Ma'lumot o'chirildi!", {
                        icon: "success",
                    });
                }

                else {

                    swal("Malumot o'chirilmadi",{
                        icon: "warning",
                    });
                }
            });
        },
        editQuestion(id){
            var temp = this.questions.find(i => i.id == id);
            this.formId = id;
            this.visible=true;
            this.question=temp;
            setTimeout(function () {
                var element = document.getElementById("form");

                element.scrollIntoView({behavior: "smooth", block: "center", inline: "center"});
            }, 0);

            // console.log('edit='+id);
        },
        qType(item){
            return item;
        },
        sanaVaqt(date) {
            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = '' + d.getFullYear(),
                hr = '' + d.getHours(),
                min = '' + d.getMinutes();
            if (month.length < 2)
                month = '0' + month;
            if (day.length < 2)
                day = '0' + day;

            if (min.length < 2)
                min = '0' + min;
            //  min= min.length<2?'0'+min : min;

            return [day, month, year].join('-') + ' ' + [hr, min].join(':');
        },
        onReset() {
            // reset form validation errors
            this.$v.$reset();

            // reset form data
            const initialData = this.$options.data.call(this);
            Object.assign(this.$data, initialData);
        },

        status(validation) {
            return {
                error: validation.$error,
                dirty: validation.$dirty
            }
        },
        changePage(page) {
            this.page = page;
            this.getData();
        },
        perviousPage() {
            this.page = page;
            console.log(this.page);
            this.getData();
        },
        isEmpty(str) {
            return (!str || str.length === 0);
        },
        checkQ() {

            return !(this.error.number.has
                && this.error.textNumber.has
                && this.error.text.has
                && this.error.type.has);

        },
        searchData(e) {
            let search = 1;
            this.getData(search);
        },
        getData(e) {
            if (e) {
                this.currentPage = 1;
                this.page = 0;
            }
            console.log(this.currentPage);
            console.log(this.page);
            axios.post("/admin/quiz/api/question/search?size=" + this.size + "&page=" + this.page,
                {
                    id: this.searchId,
                    num: this.searchNum,
                    textNumber: this.searchTextNumber,
                    text: this.searchText,
                    type: this.searchType
                })
                .then(response => {
                    this.currentPage = response.data.number + 1;
                    this.questions = response.data.content;
                    this.totalPages = response.data.totalPages;
                    this.totalElements = response.data.totalElements
                });

            // console.log("click next page ==" + this.questions.length);
            //  console.log(this.questions);


        },

        onSubmit(e) {
            // set all fields to touched
            this.$v.$touch();

            // stop here if form is invalid
            if (this.$v.$invalid){
                $.toast({
                    position: 'top-right',
                    heading: 'Xatolik',
                    text: 'Ma`lumotlarni kiriting',
                    showHideTransition: 'fade',
                    icon: 'warning'
                });
                return;  }

            const url = (this.formId !== null) ? "/admin/quiz/api/question/add?id=" + this.formId : "/admin/quiz/api/question/add";
            axios.post(url, this.question)
                .then(response => {
                    console.log(response);
                    $.toast({
                        heading: 'Ma`lumotlar saqlandi',
                        position: 'top-right',
                        icon: 'success'
                    });
                    this.question= {num: '', textNumber: '', text: '', type: ''};
                    this.visible = false;
                    this.getData();
                })
                .catch(error => {
                    $.toast({
                        position: 'top-right',
                        heading: 'Xatolik',
                        text: 'Serverda xatolik',
                        showHideTransition: 'fade',
                        icon: 'error'
                    });
                    console.log(error)
                });
        },

    },
    watch: {
        'question.num'(newVal, oldVal) {
            this.error.number.has = this.isEmpty(newVal);
        },
        'question.textNumber'(newVal, oldVal) {
            this.error.textNumber.has = (this.isEmpty(newVal)) ? true : false;
        },
        'question.text'(newVal, oldVal) {
            this.error.text.has = (this.isEmpty(newVal)) ? true : false;
        },
        'question.type'(newVal, oldVal) {
            this.error.type.has = (this.isEmpty(newVal)) ? true : false;
        },
        'text'(newVal, oldVal) {
            console.log("yangi" + newVal + "  eski=" + oldVal)
        }
    },

    computed: {

        checkButton() {
            return this.checkQ();
        },
        isInFirstPage() {
            return this.currentPage === 1;
        },
        isInLastPage() {
            return this.currentPage === this.totalPages || this.currentPage > this.totalPages;
        },

    }

});