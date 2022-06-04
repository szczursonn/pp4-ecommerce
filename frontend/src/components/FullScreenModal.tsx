import './FullScreenModal.scss'

const FullScreenModal = ({message, onClose}: {message: string, onClose: ()=>void}) => {
    return <div className="FullScreenModal">
        <div className='content'>
            <p className='message'>{message}</p>
            <button onClick={onClose}>close</button>
        </div>
    </div>
}

export default FullScreenModal