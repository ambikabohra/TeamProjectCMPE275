$(document).ready(main);

var addMultipleChoiceEditTemplate = true;
var addTrueFalseEditTemplate = true;
var addSingleAnsEditTemplate = true;

function main() {

    $('.btn-collapse').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var $collapse = $this.closest('.collapse-group').find('.collapse');
        $collapse.collapse('toggle');
    });

    /* Contact form validation */
    $('#contactForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            firstName: {
                validators: {
                    notEmpty: {
                        message: 'The first name is required'
                    }
                }
            },
            lastName: {
                validators: {
                    notEmpty: {
                        message: 'The last name is required'
                    }
                }
            },
            feedback: {
                validators: {
                    notEmpty: {
                        message: 'Your feedback is valued and required'
                    }
                }
            }
        }
    });

    /* Forgot password form validation */
    $('#forgotPasswordForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            }
        }
    });

    $('#savePasswordForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'The password and its confirmation are not the same'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The confirmation password is required'
                    },
                    identical: {
                        field: 'password',
                        message: 'The password and its confirmation are not the same'
                    }
                }
            }
        }
    });

    /* Login page form validation */
    $('#loginForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: 'The username is required'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    }
                }
            }
        }
    });

    /* Signup form validation */
    $('#signupForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            username: {
                validators: {
                    notEmpty: {
                        message: 'The username is required'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'The password and its confirm are not the same'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The confirmation password is required'
                    },
                    identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    }
                }
            },
            firstName: {
                validators: {
                    notEmpty: {
                        message: 'The first name is required'
                    }
                }
            },
            lastName: {
                validators: {
                    notEmpty: {
                        message: 'The last name is required'
                    }
                }
            },
            description: {
                validators: {
                    stringLength: {
                        message: 'Post content must be less than 300 characters',
                        min: 0,
                        max: function (value, validator, $field) {
                            return 300 - (value.match(/\r/g) || []).length;
                        }
                    }
                }
            },
            phoneNumber: {
                validators: {
                    notEmpty: {
                        message: 'The phone number is required'
                    },
                    phone: {
                        country: 'country',
                        message: 'The value is not valid %s phone number'
                    }
                }
            }
        }
    })
    // Revalidate phone number when changing the country
        .on('change', '[name="country"]', function (e) {
            $('#signupForm').formValidation('revalidateField', 'phoneNumber');
        });

}

var count = 0;


function addMultipleChoiceQues() {

    var temp = document.getElementsByTagName("template")[0];
    var clon = temp.content.cloneNode(true);
    if (clon == null || clon == "") {
        console.log("nothing here");
    } else {
        console.log(clon);
    }
    document.getElementById("placeHolder").appendChild(clon);

}

function editMultipleChoiceQuestion() {

    if (addMultipleChoiceEditTemplate) {
        var temp = document.getElementsByTagName("template")[1];
        var editTab = temp.content.cloneNode(true);
        if (editTab == null || editTab == "") {
            console.log("nothing here");
        } else {
            console.log(editTab);
        }
        document.getElementById("placeHolder").appendChild(editTab);
        addEditTemplate = false;
    }


}

function editMultipleChoiceQuesText() {

    var question = document.getElementById("questionMC");
    var quesTextFieldValue = document.getElementById("quesTextField").value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}

function changeMultipleChoiceOptionText(optionPar) {

    var option;
    var optionText;

    if (optionPar == "option1") {

        option = document.getElementById("option1");
        optionText = document.getElementById("option1Text").value;
        option.innerHTML = optionText;
    } else if (optionPar == "option2") {

        option = document.getElementById("option2");
        optionText = document.getElementById("option2Text").value;
        option.innerHTML = optionText;
    } else if (optionPar == "option3") {

        option = document.getElementById("option3");
        optionText = document.getElementById("option3Text").value;
        option.innerHTML = optionText;
    } else if (optionPar == "option4") {

        option = document.getElementById("option4");
        optionText = document.getElementById("option4Text").value;
        option.innerHTML = optionText;
    }

}


function editTrueFalseQues() {

    var temp = document.getElementsByTagName("template")[3];
    var editTab = temp.content.cloneNode(true);
    if(editTab == null || editTab == ""){
        console.log("nothing here");
    }else{
        console.log(editTab);
    }
    document.getElementById("placeHolder").appendChild(editTab);

}


function addTrueFalse() {
    if(addTrueFalseEditTemplate){
        var temp = document.getElementsByTagName("template")[2];
        var clon = temp.content.cloneNode(true);
        if(clon == null || clon == ""){
            console.log("nothing here");
        }else{
            console.log(clon);
        }
        document.getElementById("placeHolder").appendChild(clon);
        addTrueFalseEditTemplate = false;
    }
}


function editTrueFalseQuesText() {

    var question = document.getElementById("questionTF");
    var quesTextFieldValue = document.getElementById("quesTextFieldTF").value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}

function addShortAnsQues() {
    if(addSingleAnsEditTemplate){
        var temp = document.getElementsByTagName("template")[4];
        var clon = temp.content.cloneNode(true);
        if(clon == null || clon == ""){
            console.log("nothing here");
        }else{
            console.log(clon);
        }
        document.getElementById("placeHolder").appendChild(clon);
        addTrueFalseEditTemplate = false;
    }
}

function AddShortAnsQuesTextBox() {

    var temp = document.getElementsByTagName("template")[5];
    var editTab = temp.content.cloneNode(true);
    if(editTab == null || editTab == ""){
        console.log("nothing here");
    }else{
        console.log(editTab);
    }
    document.getElementById("placeHolder").appendChild(editTab);

}

function editShortAnsQuesText() {

    var question = document.getElementById("questionSA");
    var quesTextFieldValue = document.getElementById("quesTextFieldSA").value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}

/*
function markChecked(star) {
    var starNum = star.substr(star.lastIndexOf('r'),1);
    console.log(starNum);
    while()
    var star = document.getElementById(star);
    if(flag == "unchecked"){
        star.setAttribute('class','fa fa-star checked');
        flag = "checked";
    }else{
        star.setAttribute('class','fa fa-star');
        flag = "unchecked";
    }
}*/


