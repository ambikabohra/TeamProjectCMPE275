$(document).ready(main);

var addMultipleChoiceEditTemplate = true;
var addTrueFalseEditTemplate = true;
var addSingleAnsEditTemplate = true;
var starEditTemplate = true;
var addRadioEditTemplate = true;
var addDropdownEditTemplate = true;
var addImageEditTemplate = true;

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


    /* set survey form validation */
    $('#setSurvey').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            surveyName: {
                validators: {
                    notEmpty: {
                        message: 'The survey name is required'
                    }
                }
            },
            surveyDesc: {
                validators: {
                    notEmpty: {
                        message: 'The survey description is required'
                    }
                }
            },
            days: {
                validators: {
                    notEmpty: {
                        message: 'Please enter number of days after which survey should expire, else enter 0'
                    },
                    integer: {
                        message: 'Only Integers are allowed here'
                    }
                }
            },
            hours: {
                validators: {
                    notEmpty: {
                        message: 'Please enter number of hours after which survey should expire, else enter 0'
                    },
                    integer: {
                        message: 'Only Integers are allowed here'
                    }
                }
            },
            minutes: {
                validators: {
                    notEmpty: {
                        message: 'Please enter number of minutes after which survey should expire, cannot enter 0 if hours and minutes are 0'
                    },
                    integer: {
                        message: 'Only Integers are allowed here'
                    }
                }
            }
        }
    });

    /* Contact form validation */
    $('#editoptions').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            multipleChoiceQuesTextField: {
                validators: {
                    notEmpty: {
                        message: 'Please enter question text'
                    }
                }
            },
            option1Text: {
                validators: {
                    notEmpty: {
                        message: 'Please enter option text'
                    }
                }
            },
            option2Text: {
                validators: {
                    notEmpty: {
                        message: 'Please enter option text'
                    }
                }
            },
            option3Text: {
                validators: {
                    notEmpty: {
                        message: 'Please enter option text'
                    }
                }
            },
            option4Text: {
                validators: {
                    notEmpty: {
                        message: 'Please enter option text'
                    }
                }
            }
        }
    });



}


var count = 0;

var addquestionflag = true;

function addMultipleChoiceQues() {

    if (addquestionflag == true) {
        var temp = document.getElementsByTagName("template")[0];
        var clon = temp.content.cloneNode(true);
        if (clon == null || clon == "") {
            console.log("nothing here");
        } else {
            console.log(clon);
        }
        document.getElementById("placeHolder").appendChild(clon);
        addquestionflag = false;
    }
}

function editMultipleChoiceQuesText() {

    var question = document.getElementById("questionMC");
    var quesTextFieldValue = document.getElementById("multipleChoiceQuesTextField").value;
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


function addImageQues() {

    if (addquestionflag == true) {
        var temp = document.getElementsByTagName("template")[12];
        var clon = temp.content.cloneNode(true);
        if (clon == null || clon == "") {
            console.log("nothing here");
        } else {
            console.log(clon);
        }
        document.getElementById("placeHolder").appendChild(clon);
        addquestionflag = false;
    }
}

function editImageQuesText() {

    var question = document.getElementById("questionIMG");
    var quesTextFieldValue = document.getElementById("imageQuesTextField").value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}

function addTrueFalse() {

    var temp = document.getElementsByTagName("template")[2];
    var clon = temp.content.cloneNode(true);
    if (clon == null || clon == "") {
        console.log("nothing here");
    } else {
        console.log(clon);
    }
    document.getElementById("placeHolder").appendChild(clon);
    addTrueFalseEditTemplate = false;
}


function editTrueFalseQuesText() {

    var question = document.getElementById("questionTF");
    var quesTextFieldValue = document.getElementById("quesTextFieldTF").value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);
}

function addShortAnsQues() {

    if (addquestionflag == true) {

        var temp = document.getElementsByTagName("template")[4];
        var clon = temp.content.cloneNode(true);
        if (clon == null || clon == "") {
            console.log("nothing here");
        } else {
            console.log(clon);
        }
        document.getElementById("placeHolder").appendChild(clon);
        addTrueFalseEditTemplate = false;
        addquestionflag = false;
    }
}


function editShortAnsQuesText() {

    var question = document.getElementById("questionSA");
    var quesTextFieldValue = document.getElementById("quesTextFieldSA").value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}

function addStarRatingQues() {

    if (addquestionflag == true) {
        var temp = document.getElementsByTagName("template")[6];
        var clon = temp.content.cloneNode(true);
        if (clon == null || clon == "") {
            console.log("nothing here");
        } else {
            console.log(clon);
        }
        document.getElementById("placeHolder").appendChild(clon);
        addquestionflag = false;
    }
}

function editStarRatingQuesText() {

    var question = document.getElementById("questionStar");
    var quesTextFieldValue = document.getElementById("quesTextStarRatingField").value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}

//Radio

function addRadioQues() {

    if (addquestionflag == true) {
        var temp = document.getElementsByTagName("template")[8];
        var clon = temp.content.cloneNode(true);
        if (clon == null || clon == "") {
            console.log("nothing here");
        } else {
            console.log(clon);
        }
        document.getElementById("placeHolder").appendChild(clon);
        addquestionflag = false;
    }
}

function editRadioText() {

    var question = document.getElementById("questionRadio");
    var quesTextFieldValue = document.getElementById("radioTextField").value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}

function changeRadioText(optionPar) {

    var option;
    var optionText;

    if (optionPar == "radio1") {

        option = document.getElementById("radio1");
        optionText = document.getElementById("radio1Text").value;
        option.innerHTML = optionText;
    } else if (optionPar == "radio2") {

        option = document.getElementById("radio2");
        optionText = document.getElementById("radio2Text").value;
        option.innerHTML = optionText;
    } else if (optionPar == "radio3") {

        option = document.getElementById("radio3");
        optionText = document.getElementById("radio3Text").value;
        option.innerHTML = optionText;
    } else if (optionPar == "radio4") {

        option = document.getElementById("radio4");
        optionText = document.getElementById("radio4Text").value;
        option.innerHTML = optionText;
    }

}

//Dropdown

function addDropdownQues() {

    if (addquestionflag == true) {
        var temp = document.getElementsByTagName("template")[10];
        var clon = temp.content.cloneNode(true);
        if (clon == null || clon == "") {
            console.log("nothing here");
        } else {
            console.log(clon);
        }
        document.getElementById("placeHolder").appendChild(clon);
        addquestionflag = false;
    }
}


function editDropdownText() {

    var question = document.getElementById("questionDropdown");
    var quesTextFieldValue = document.getElementById("dropdownTextField").value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}

function changeDropdownText(optionPar) {

    var option;
    var optionText;

    if (optionPar == "dropdown1") {

        option = document.getElementById("dropdown1");
        optionText = document.getElementById("dropdown1Text").value;
        option.innerHTML = optionText;
    } else if (optionPar == "dropdown2") {

        option = document.getElementById("dropdown2");
        optionText = document.getElementById("dropdown2Text").value;
        option.innerHTML = optionText;
    } else if (optionPar == "dropdown3") {

        option = document.getElementById("dropdown3");
        optionText = document.getElementById("dropdown3Text").value;
        option.innerHTML = optionText;
    } else if (optionPar == "dropdown4") {

        option = document.getElementById("dropdown4");
        optionText = document.getElementById("dropdown4Text").value;
        option.innerHTML = optionText;
    }

}


//Add edit question text and options text fragment- common for all question types

function editQuestion() {

    var questionType = document.getElementById("questionType").innerText;
    console.log("question type ", questionType);

    if (questionType == "MultipleChoice") {
        if (addMultipleChoiceEditTemplate) {
            var temp = document.getElementsByTagName("template")[1];
            var editTab = temp.content.cloneNode(true);
            if (editTab == null || editTab == "") {
                console.log("nothing here");
            } else {
                console.log(editTab);
            }
            document.getElementById("placeHolder").appendChild(editTab);
            addMultipleChoiceEditTemplate = false;
        }
    } else if (questionType == "ShortAnswer") {

        if (addSingleAnsEditTemplate) {
            var temp = document.getElementsByTagName("template")[5];
            var editTab = temp.content.cloneNode(true);
            if (editTab == null || editTab == "") {
                console.log("nothing here");
            } else {
                console.log(editTab);
            }
            document.getElementById("placeHolder").appendChild(editTab);
            addSingleAnsEditTemplate = false;
        }

    } else if (questionType == "StarRating") {

        if (starEditTemplate) {
            var temp = document.getElementsByTagName("template")[7];
            var editTab = temp.content.cloneNode(true);
            if (editTab == null || editTab == "") {
                console.log("nothing here");
            } else {
                console.log(editTab);
            }
            document.getElementById("placeHolder").appendChild(editTab);
            starEditTemplate = false;
        }

    } else if (questionType == "Radio") {

        if (addRadioEditTemplate) {
            var temp = document.getElementsByTagName("template")[9];
            var editTab = temp.content.cloneNode(true);
            if (editTab == null || editTab == "") {
                console.log("nothing here");
            } else {
                console.log(editTab);
            }
            document.getElementById("placeHolder").appendChild(editTab);
            addRadioEditTemplate = false;
        }

    } else if (questionType == "Dropdown") {

        if (addDropdownEditTemplate) {
            var temp = document.getElementsByTagName("template")[11];
            var editTab = temp.content.cloneNode(true);
            if (editTab == null || editTab == "") {
                console.log("nothing here");
            } else {
                console.log(editTab);
            }
            document.getElementById("placeHolder").appendChild(editTab);
            addDropdownEditTemplate = false;
        }

    } else if (questionType == "TrueFalse") {

        if (addTrueFalseEditTemplate) {
            var temp = document.getElementsByTagName("template")[3];
            var editTab = temp.content.cloneNode(true);
            if (editTab == null || editTab == "") {
                console.log("nothing here");
            } else {
                console.log(editTab);
            }
            document.getElementById("placeHolder").appendChild(editTab);
            addTrueFalseEditTemplate = false;
        }
    } else if (questionType == "ImageQuestion") {

        if (addImageEditTemplate) {
            var temp = document.getElementsByTagName("template")[13];
            var editTab = temp.content.cloneNode(true);
            if (editTab == null || editTab == "") {
                console.log("nothing here");
            } else {
                console.log(editTab);
            }
            document.getElementById("placeHolder").appendChild(editTab);
            addImageEditTemplate = false;
        }
    }
}



/*------------------------------------------------------------------------------------------------------------*/
//For Edit Survey Part

var addEditQuestion = [];
var i = 0;

function editQuestionText(quesId) {

    if(!addEditQuestion.includes(quesId)){
        console.log("Inside editQuestionText", quesId);
        var temp = document.getElementsByTagName("template")[0];
        var clon = temp.content.cloneNode(true);
        if (clon == null || clon == "") {
            console.log("nothing here");
        } else {
            //console.log(clon);
        }
        document.getElementById("placeHolder"+quesId).appendChild(clon);
        addEditQuestion[i] = quesId;
        i++;
    }
}

function editSurveyEditMultipleChoiceQuesText(event) {

    console.log("inside editSurveyEditMultipleChoiceQuesText", event.target.id);

    var question = document.getElementById('question' + event.target.id);
    console.log("question", question.innerText);
    var quesTextFieldValue = document.getElementById(event.target.id).value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}

function editChangeMultipleChoiceOptionText(event) {

    var id = event.target.id;

    console.log("id in changeMultipleChoiceOptionText", id);

    var questionId = id.substr(6, id.length - 8);

    console.log("question id in changeMultipleChoiceOptionText", questionId);

    var optionNum = id.substr(id.length - 2, 1);

    console.log("option Num in changeMultipleChoiceOptionText", optionNum);

    if (optionNum == 1) {

        var option1Text = document.getElementById(id).value;
        document.getElementById('option' + questionId + optionNum).innerText = option1Text;

    } else if (optionNum == 2) {

        var option1Text = document.getElementById(id).value;
        document.getElementById('option' + questionId + optionNum).innerText = option1Text;

    } else if (optionNum == 3) {

        var option1Text = document.getElementById(id).value;
        document.getElementById('option' + questionId + optionNum).innerText = option1Text;

    } else if (optionNum == 4) {

        var option1Text = document.getElementById(id).value;
        document.getElementById('option' + questionId + optionNum).innerText = option1Text;

    }

}

function editSurveyEditSingleAnswerQuesText(event) {

    console.log("inside editSurveyEditSingleAnswerQuesText", event.target.id);

    var question = document.getElementById('question' + event.target.id);
    console.log("question", question.innerText);
    var quesTextFieldValue = document.getElementById(event.target.id).value;
    question.innerHTML = quesTextFieldValue;
    console.log(quesTextFieldValue);

}






