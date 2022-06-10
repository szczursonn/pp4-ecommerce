import styles from './FullScreenModal.module.scss'

const FullScreenModal = ({message, onClose}: {message: string, onClose: ()=>void}) => {
    return <div className={styles.container}>
        <div className={styles['content-container']}>
            <p className={styles.message}>{message}</p>
            <button className={styles['btn-close']} onClick={onClose}>CLOSE</button>
        </div>
    </div>
}

export default FullScreenModal