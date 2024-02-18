const minLengthValidator = (value, minLength) => {
    return value.length >= minLength;
};

const requiredValidator = value => {
    return value.trim() !== '';
};

const roleValidator = value => {
    return value === 'admin' || value === 'client';
};

const validate = (value, rules) => {
    let isValid = true;

    for (let rule in rules) {

        switch (rule) {
            case 'minLength': isValid = isValid && minLengthValidator(value, rules[rule]);
                break;

            case 'isRequired': isValid = isValid && requiredValidator(value);
                break;

            case 'type': isValid = isValid && roleValidator(value);
                break;

            default: isValid = true;
        }

    }

    return isValid;
};

export default validate;