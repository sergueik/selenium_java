function select(values) {
    if (this.nodeName.toLowerCase() !== 'select')
        throw new Error('Element is not a <select> element.');

    const options = Array.from(this.options);
    this.value = undefined;
    for (const option of options) {
        option.selected = values.includes(option.value);
        if (option.selected && !this.multiple)
            break;
    }
    this.dispatchEvent(new Event('input', { 'bubbles': true }));
    this.dispatchEvent(new Event('change', { 'bubbles': true }));
    return options.filter(option => option.selected).map(option => option.value);
}