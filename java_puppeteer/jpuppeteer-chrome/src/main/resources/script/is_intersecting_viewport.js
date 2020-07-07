async function isIntersectingViewport() {
    const self = this;
    const visibleRatio = await new Promise(resolve => {
        const observer = new IntersectionObserver(entries => {
            resolve(entries[0].intersectionRatio);
            observer.disconnect();
        });
        observer.observe(self);
    });
    return visibleRatio > 0;
}