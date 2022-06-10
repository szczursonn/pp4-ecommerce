import converter from './ProductIdConverter'

describe('ProductIdConverter tests', () => {
    test('fakeId to realId', () => {
        expect(converter.toRealId('lego-set-1-1')).toEqual('1')
        expect(converter.toRealId('1-legfd-1')).toEqual('1')
        expect(converter.toRealId('5')).toEqual('5')
        expect(converter.toRealId('1-2-3')).toEqual('3')
    })

    test('realId to fakeId', () => {
        expect(converter.toFakeId('1', 'Lego set 1')).toEqual('lego-set-1-1')
        expect(converter.toFakeId('3', '5g808')).toEqual('5g808-3')
    })
})

export {}