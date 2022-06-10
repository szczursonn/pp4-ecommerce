// stolen from allegro, example: /products/1 = /products/bardzo-fajny-lego-set-1
const toRealId = (fakeId: string): string => {
    const x = fakeId.split('-')
    return x[x.length-1]
}
const toFakeId = (realId: string | number, name: string): string => encodeURIComponent(name.toLowerCase().replace(/ /g, '-')) + '-' + realId

const ProductIdConverter = {
    toFakeId,
    toRealId
}

export default ProductIdConverter