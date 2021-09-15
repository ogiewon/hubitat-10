/**
*	Super Fan
*
*    Author:
*        Adam Kempenich
*
*/
definition(
    name:"MagicHome Manager",
    namespace: "MagicHome",
    author: "Adam Kempenich",
    description: "Discover lights, manage their IPs, and create custom effects",
    category: "Convenience",
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: "",
    importURL: ""
)

preferences {
    page name: "mainPage", title: "", install: true, uninstall: true
} 

def installed() {
    log.info "Installed with settings: ${settings}"
    state.initializeCompleted = true
    initialize()
}


def updated() {
    log.info "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}

def initialize() {
    childApps.each { child ->
        logInformationText "Child app: ${child.label}"
    }
}

def mainPage() {
    dynamicPage(name: "mainPage", refreshInterval:0){

        section("<h2>Information</h2>"){
            paragraph "This app will help you manage your MagicHome devices without needing the app. While I recommend that you reserve an IP for your devices on your router, this app will do its best to find lights if they change their IP on your network."    
            label title: "<h2>Want to give MagicHome Manager a different name? (optional)</h2>", required: false, submitOnChange: true
        }

        section("<h2>Pixel Controller Effect Builder</h2>", hideable: true, hidden: true ){
            paragraph "There's a good chance this only works on desktop. Tested in FireFox.<br>"
            
            paragraph """
                        <iframe style="width: 100%; height: 100vh; display: block;" src="data:text/html;base64,PCEtLSBMZWZ0IG9mZiBhZnRlciBjb21wbGV0aW5nIHRoZSBpbnRpYWwgcGFydCBvZiB0aGUgZm9ybQ0KDQoJTmV4dCBzdGVwczogY3JlYXRlIGFuIGl0ZXJhdGluZyBsb29wIHRoYXQgbGV0cyB5b3UgYWRkIG1vcmUgZnVuY3Rpb25zDQoJUHJldHRpZnkNCg0KDQpdIC0tPg0KDQo8IWRvY3R5cGUgaHRtbD4NCjxoZWFkPg0KPHN0eWxlIHR5cGU9InRleHQvY3NzIj4NCglib2R5ew0KCQlmb250LWZhbWlseTogSGVsdmV0aWNhLCBBcmlhbCwgU2Fucy1TZXJpZjsNCgkJZm9udC13ZWlnaHQ6IDQwMDsNCgkJbWFyZ2luLWxlZnQ6IDUlOw0KCQltYXJnaW4tcmlnaHQ6IDUlOw0KCX0NCglmb3JtID4gZGl2ew0KDQoJfQ0KCQ0KCSNjb21waWxlZHsNCgkJZGlzcGxheTogYmxvY2s7DQoJCW1heC13aWR0aDogODAlOw0KCQlvdmVyZmxvdy13cmFwOiBhbnl3aGVyZTsNCgkJaGVpZ2h0OiAgYXV0bzsNCgkJd2lkdGg6ICA4MCU7DQoJCWZvbnQtc2l6ZTogMS41ZW07DQoJfQ0KDQoJdGR7DQoJCXRleHQtYWxpZ246IGNlbnRlcjsNCgkJcGFkZGluZy1sZWZ0OiAgMWVtOw0KCQlwYWRkaW5nLXJpZ2h0OiAgMWVtOw0KCX0NCglzZWxlY3R7DQoJCW1heC13aWR0aDogMjBlbTsNCgkJb3ZlcmZsb3c6IGhpZGRlbjsNCgl9DQo8L3N0eWxlPg0KDQo8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCI+DQoNCmZ1bmN0aW9uIGJ1aWxkRm9ybShmb3JtKXsNCg0KCWZvcihpPTE7IGkgPD0gMzI7IGkrKyl7DQoJdmFyIGRpdiA9IGRvY3VtZW50LmNyZWF0ZUVsZW1lbnQoJ0RJVicpOw0KCXZhciB3cml0ZUhUTUwgPSBgDQoJPGRpdiBpZD0iJHtpfSI+PGgyPkVmZmVjdCAke2l9PC9oMj4NCg0KCTx0YWJsZT4NCgkJPHRyPg0KCQkJPHRkPjxoMz5FZmZlY3QgVHlwZTo8L2gzPjwvdGQ+DQoJCQk8dGQ+PGgzPkNvbG9yIDE6PC9oMz48L3RkPg0KCQk8dGQ+PGgzPkNvbG9yIDI6PC9oMz48L3RkPg0KCQk8dGQ+PGgzPlNwZWVkICgxLTEwMCUpOjxoMz48L3RkPg0KCTwvdHI+CQ0KCTx0cj4NCgk8dGQ+DQoJPHNlbGVjdCBuYW1lPSJlZmZlY3QtJHtpfSIgaWQ9ImVmZmVjdC0ke2l9IiBvbkNoYW5nZT0id3JpdGVIVE1MKHRoaXMuZm9ybSkiPg0KCQk8b3B0aW9uIHZhbHVlPSIwIiBzZWxlY3RlZD5ObyBFZmZlY3Q8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMSI+Q2hhbmdlIEdyYWR1YWxseTwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIyIj5CcmlnaHQgVXAgYW5kIEZhZGUgZ3JhZHVhbGx5PC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjMiPkNoYW5nZSBxdWlja2x5PC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjQiPlN0cm9iZS1mbGFzaDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSI1Ij5SdW5uaW5nLCAxIHBvaW50LCBTdGFydCB0byBlbmQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iNiI+UnVubmluZywgMSBwb2ludCwgRW5kIHRvIHN0YXJ0PC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjciPlJ1bm5pbmcsIDEgcG9pbnQsIGZyb20gbWlkZGxlIHRvIGJvdGggZW5kczwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSI4Ij5SdW5uaW5nLCAxIHBvaW50LCBmcm9tIGJvdGggZW5kcyB0byBtaWRkbGU8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iOSI+T3ZlcmxheSwgZnJvbSBzdGFydCB0byBlbmQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMTAiPk92ZXJsYXksIGZyb20gZW5kIHRvIHN0YXJ0PC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjExIj5PdmVybGF5LCBmcm9tIG1pZGRsZSB0byBib3RoIGVuZHM8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMTIiPk92ZXJsYXksIGZyb20gYm90aCBlbmRzIHRvIG1pZGRsZTwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIxMyI+RmFkaW5nIGFuZCBydW5uaW5nLCAxIHBvaW50LCBmcm9tIGVuZCB0byBzdGFydDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIxNCI+RmFkaW5nIGFuZCBydW5uaW5nLCAxIHBvaW50LCBmcm9tIGVuZCB0byBzdGFydDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIxNSI+T2xpdmFyeSBmbG93bmluZywgZnJvbSBzdGFydCB0byBlbmQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMTYiPk9saXZhcnkgZmxvd2luZywgZnJvbSBlbmQgdG8gc3RhcnQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMTciPlJ1bm5pbmcsIDEgcG9pbnQgdy9iYWNrZ3JvdW5kIGZyb20gc3RhcnQgdG8gZW5kPC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjE4Ij5SdW5uaW5nLCAxIHBvaW50IHcvIGJhY2tncm91bmQgZnJvbSBlbmQgdG8gc3RhcnQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMTkiPjIgY29sb3JzIHJ1biwgbXVsdGkgcG9pbnRzIHcvIGJsYWNrIGJrZyBmcm9tIHN0YXJ0IHRvIGVuZDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIyMCI+MiBjb2xvcnMgcnVuLCBtdWx0aSBwb2ludHMgdyAvYmxhY2sgYmtnIGZyb20gZW5kIHRvIHN0YXJ0PC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjIxIj4yIGNvbG9ycyBydW4gYWx0ZXJuYXRlbHksIGZhZGluZyBmcm9tIHN0YXJ0IHRvIGVuZDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIyMiI+MiBjb2xvcnMgcnVuIGFsdGVybmF0ZWx5LCBmYWRpbmcgZnJvbSBlbmQgdG8gc3RhcnQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMjMiPjIgY29sb3JzIHJ1biBhbHRlcm5hdGVseSwgbXVsdGkgcG9pbnRzIGZyb20gc3RhcnQgdG8gZW5kPC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjI0Ij4yIGNvbG9ycyBydW4gYXRsZXJuYXRlbHksIG11bHRpIHBvaW50cyBmcm9tIGVuZCB0byBzdGFydCA8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMjUiPkZhZGluZyBvdXQgZmxvd3MsIGZyb20gc3RhcnQgdG8gZW5kPC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjI2Ij5GYWRpbmcgb3V0IGZsb3dzLCBmcm9tIGVuZCB0byBzdGFydDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIyNyI+NyBjb2xvcnMgcnVuIGFsdGVybmF0ZWx5LCAxIHBvaW50IHcvIG11bHRpIHBvaW50cyBia2csIGZyb20gc3RhcnQgdG8gZW5kPC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjI4Ij43IGNvbG9ycyBydW4gYWx0ZXJuYXRlbHksIDEgcG9pbnQgdy8gbXVsdGkgcG9pbnRzIGJrZywgZnJvbSBlbmQgdG8gc3RhcnQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMjkiPjcgY29sb3JzIHJ1biBhbHRlcm5hdGVseSwgMSBwb2ludCBmcm9tIHN0YXJ0IHRvIGVuZDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIzMCI+NyBjb2xvcnMgcnVuIGFsdGVybmF0ZWx5IDEgcG9pbnQgZnJwbSBlbmQgdG8gc3RhcnQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMzEiPjcgY29sb3JzIHJ1biBhbHRlcm5hdGVseSwgbXVsdGkgcG9pbnRzLCBmcm9tIHN0YXJ0IHRvIGVuZDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIzMiI+NyBjb2xvcnMgcnVuIGFsdGVybmF0ZWx5LCBtdWx0aSBwb2ludHMsIGZyb20gZW5kIHRvIHN0YXJ0PC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjMzIj43IGNvbG9ycyBvdmVybGF5LCBtdWx0aSBwb2ludHMsIGZvcm0gc3RhcnQgdG8gZW5kPC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjM0Ij43IGNvbG9ycyBvdmVybGF5IG11bHRpIHBvaW50cywgZnJvbSBlbmQgdG8gc3RhcnQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMzUiPjcgQ29sb3JzIG92ZXJsYXksIG11bHRpIHBvaW50cyBmcm9tIHRoZSBtaWRkbGUgdG8gYm90aCBlbmRzPC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjM2Ij43IGNvbG9ycyBvdmVybGF5LCBtdWx0aSBwb2ludHMsIGZyb20gYm90aCBlbmRzIHRvIHRoZSBtaWRkbGU8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iMzciPjcgY29sb3JzIGZsb3cgZ3JhZHVhbGx5LCBmcm9tIHN0YXJ0IHRvIGVuZDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSIzOCI+NyBjb2xvcnMgZmxvdyBncmFkdWFsbHksIGZyb20gZW5kIHRvIHN0YXJ0PC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjM5Ij5GYWRpbmcgb3V0IHJ1biwgNyBjb2xvcnMgZnJvbSBzdGFydCB0byBlbmQ8L29wdGlvbj4NCgkJPG9wdGlvbiB2YWx1ZT0iNDAiPkZhZGluZyBvdXQgcnVuLCA3IGNvbG9ycyBmcm9tIGVuZCB0byBzdGFydDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSI0MSI+UnVucyBpbiBvbGl2YXJ5LCA3IGNvbG9ycyBmcm9tIHN0YXJ0IHRvIGVuZDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSI0MiI+UnVucyBpbiBvbGl2YXJ5LCA3IGNvbG9ycyBmcm9tIGVuZCB0byBzdGFydDwvb3B0aW9uPg0KCQk8b3B0aW9uIHZhbHVlPSI0MyI+RmFkaW5nIG91dCBydW4sIDcgY29sb3JzIHN0YXJ0IHdpdGggd2hpdGUgY29sb3IgZnJvbSBzdGFydCB0byBlbmQgPC9vcHRpb24+DQoJCTxvcHRpb24gdmFsdWU9IjQ0Ij5GYWRpbmcgb3V0IHJ1biwgNyBjb2xvcnMgc3RhcnQgd2l0aCB3aGl0ZSBjb2xvciBmcm9tIGVuZCB0byBzdGFydDwvb3B0aW9uPg0KCTwvc2VsZWN0Pg0KCQk8L3RkPg0KCQk8dGQ+PGlucHV0IHR5cGU9ImNvbG9yIiBpZD0icmdiMS0ke2l9IiBvbkNoYW5nZT0id3JpdGVIVE1MKHRoaXMuZm9ybSkiPjwvdGQ+DQoJCTx0ZD48aW5wdXQgdHlwZT0iY29sb3IiIGlkPSJyZ2IyLSR7aX0iIG9uQ2hhbmdlPSJ3cml0ZUhUTUwodGhpcy5mb3JtKSI+PC90ZD4NCgkJPHRkPjxpbnB1dCB0eXBlPSJyYW5nZSIgaWQ9InNwZWVkLSR7aX0iIG1pbj0iMCIgbWF4PSIxMDAiIHZhbHVlPSIwIiBvbkNoYW5nZT0id3JpdGVIVE1MKHRoaXMuZm9ybSkiPjwvdGQ+DQoJPC90cj4NCgk8dHI+DQoJPHRkPjwvdGQ+DQoJPHRkPjwvdGQ+DQoJPHRkPihpZiBzdXBwb3J0ZWQgYnkgZWZmZWN0KTwvdGQ+DQoJPHRkPjxwIGlkPSJyYW5nZS0ke2l9Ij4wPC9wPjwvdGQ+DQoJPC90YWJsZT4NCgkNCgkNCgkNCgk8YnI+DQoJPGhyPg0KDQoJPC9kaXY+YDsNCg0KCWRpdi5pbm5lckhUTUwgPSB3cml0ZUhUTUw7DQoJDQoJZG9jdW1lbnQuZ2V0RWxlbWVudEJ5SWQoJ2JpZ0Zvcm0nKS5hcHBlbmRDaGlsZChkaXYpOwkNCgl9DQp9DQoNCg0KDQpmdW5jdGlvbiB0ZXN0UmVzdWx0cyAoZm9ybSkgew0KICAgIHZhciBlZmZlY3ROdW1iZXIgPSBmb3JtLmVmZmVjdC52YWx1ZTsNCiAgICB2YXIgcmdiMSA9IGZvcm0ucmdiMS52YWx1ZTsNCiAgICB2YXIgcmdiMiA9IGZvcm0ucmdiMi52YWx1ZTsNCiAgICB2YXIgc3BlZWQgPSBmb3JtLnNwZWVkLnZhbHVlOw0KICAgIGFsZXJ0IChlZmZlY3ROdW1iZXIgKyAiICIgKyByZ2IxICsgIiAiICsgcmdiMiArICIgIiArIHNwZWVkKTsNCn0NCg0KDQp2YXIgZm9ybSA9IGRvY3VtZW50LnF1ZXJ5U2VsZWN0b3IoJ2Zvcm0nKTsNCg0KZnVuY3Rpb24gd3JpdGVIVE1MKGZvcm0pew0KCXZhciBiaWdTdHJpbmcgPSAiNTFGMCI7DQoJdmFyIG5leHRJdGVtVmlzaWJsZSA9IHRydWU7DQoNCglmb3IoaT0xOyBpIDw9IDMyOyBpKyspew0KCQkvL2FsZXJ0KGBlZmZlY3QtJHtpfWApOw0KCXZhciBlZmZlY3ROdW1iZXIgPSBkZWNUb0hleFN0cmluZyhwYXJzZUludChkb2N1bWVudC5nZXRFbGVtZW50QnlJZChgZWZmZWN0LSR7aX1gKS52YWx1ZSkpOw0KCWlmKGkhPTEpew0KCQl2YXIgcHJpb3JFZmZlY3ROdW1iZXIgPSBkZWNUb0hleFN0cmluZyhwYXJzZUludChkb2N1bWVudC5nZXRFbGVtZW50QnlJZChgZWZmZWN0LSR7aS0xfWApLnZhbHVlKSk7fQ0KDQoNCgl2YXIgcmdiMTsNCgl2YXIgcmdiMjsNCgl2YXIgc3BlZWQ7DQoNCglpZigocHJpb3JFZmZlY3ROdW1iZXIgPT0gIjAwIiB8fCBuZXh0SXRlbVZpc2libGUgPT0gZmFsc2UpICYmIGkgIT0gMSl7DQoJCW5leHRJdGVtVmlzaWJsZSA9IGZhbHNlOw0KCQlyZ2IxID0gIiMwMDAwMDAiOw0KCQlyZ2IyID0gIiMwMDAwMDAiOw0KCQlzcGVlZCA9ICIwMCI7DQoJCWRvY3VtZW50LmdldEVsZW1lbnRCeUlkKGkpLnN0eWxlLmRpc3BsYXkgPSAibm9uZSI7DQoJfSBlbHNlIHsNCgkJbmV4dEl0ZW1WaXNpYmxlID0gdHJ1ZTsNCiAgICAJcmdiMSA9IGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKGByZ2IxLSR7aX1gKS52YWx1ZSB8fCAiIzAwMDAwMCI7DQogICAgCXJnYjIgPSBkb2N1bWVudC5nZXRFbGVtZW50QnlJZChgcmdiMi0ke2l9YCkudmFsdWUgfHwgIiMwMDAwMDAiOw0KICAgIAlzcGVlZCA9IGRlY1RvSGV4U3RyaW5nKHBhcnNlSW50KGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKGBzcGVlZC0ke2l9YCkudmFsdWUpKSB8fCAiMDAiOw0KCQlkb2N1bWVudC5nZXRFbGVtZW50QnlJZChpKS5zdHlsZS5kaXNwbGF5ID0gImJsb2NrIjsNCg0KICAgIH0NCg0KICAgIGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKGByYW5nZS0ke2l9YCkuaW5uZXJIVE1MID0gIiIgKyBkb2N1bWVudC5nZXRFbGVtZW50QnlJZChgc3BlZWQtJHtpfWApLnZhbHVlICsgIiUiOw0KDQoJdmFyIGh0bWxTdHJpbmcgPSAiRWZmZWN0OiAiICsgZWZmZWN0TnVtYmVyICsgIiBSR0IxOiAiICsgcmdiMSArICIgUkdCMjogIiArIHJnYjIgKyAiIFNwZWVkOiAiICsgc3BlZWQ7DQoJdmFyIGNvbXBpbGVkU3RyaW5nID0gIiIgKyBlZmZlY3ROdW1iZXIgKyBzcGVlZCArIHJnYjEuc2xpY2UoMSkgKyByZ2IyLnNsaWNlKDEpICsgImYwIg0KCQliaWdTdHJpbmcgKz0gY29tcGlsZWRTdHJpbmc7DQoJfQ0KICAgIGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKCJjb21waWxlZCIpLnZhbHVlID0gYmlnU3RyaW5nOw0KfQ0KDQpmdW5jdGlvbiBkZWNUb0hleFN0cmluZyhkZWMpIHsNCiAgIHJldHVybiAoZGVjKzB4MTAwMDApLnRvU3RyaW5nKDE2KS5zdWJzdHIoLTIpOw0KfQ0KDQpmdW5jdGlvbiBjaGVja0Zvcm1Db21wbGV0aW9uKGZvcm0pew0KDQoJdmFyIHRvdGFsRWxlbWVudHNDb21wbGV0ZWQ7DQoNCglmb3IoaT0xOyBpIDwgMzM7IGkrKyl7DQoJCXRyeXsNCgkJCQ0KCQkJdmFyIGNoZWNrID0gZG9jdW1lbnQuZ2V0RWxlbWVudEJ5SWQoaSk7DQoJCSAgICBkb2N1bWVudC5nZXRFbGVtZW50QnlJZCgiY3VycmVudFdyaXRlIikuaW5uZXJIVE1MID0gY2hlY2s7DQoJCX0NCgkJY2F0Y2goZSl7DQoJCQlhbGVydCgiU3RvcHBlZCBhdCAiICsgaSk7DQoJCX0NCg0KCX0NCg0KDQp9DQoNCmZ1bmN0aW9uIGNvcHlUb0NsaXBib2FyZCgpew0KDQoJLyogR2V0IHRoZSB0ZXh0IGZpZWxkICovDQogIHZhciBjb3B5VGV4dCA9IGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKCJjb21waWxlZCIpOw0KDQogIC8qIFNlbGVjdCB0aGUgdGV4dCBmaWVsZCAqLw0KICBjb3B5VGV4dC5mb2N1cygpOw0KICBjb3B5VGV4dC5zZWxlY3QoKTsgDQogIGNvcHlUZXh0LnNldFNlbGVjdGlvblJhbmdlKDAsIDk5OTk5KTsgLyogRm9yIG1vYmlsZSBkZXZpY2VzICovDQoNCiAgIC8qIENvcHkgdGhlIHRleHQgaW5zaWRlIHRoZSB0ZXh0IGZpZWxkICovDQogIG5hdmlnYXRvci5jbGlwYm9hcmQud3JpdGVUZXh0KGNvcHlUZXh0LnZhbHVlKTsNCg0KDQp9DQoNCjwvc2NyaXB0Pg0KDQo8L2hlYWQ+DQoNCjxib2R5Pg0KDQoJPGgxPk1hZ2ljSG9tZSBQaXhlbCBFZmZlY3QgQnVpbGRlciAoRm9yIFdTMjgxMSBjb250cm9sbGVycyk8L2gxPg0KCTxoMz5Db3B5IHRoaXMgYW5kIHBhc3RlIGl0IGludG8geW91ciBlZmZlY3Qgc3RvcmFnZSBpbiB5b3VyIEh1Yml0YXQgZGV2aWNlIHBhZ2U6PC9oMz4NCg0KCTxidXR0b24gb25jbGljaz0iY29weVRvQ2xpcGJvYXJkKCkiPjxoMz5Db3B5IHRoaXMgdG8gdGhlIGNsaXBib2FyZDwvaDM+PC9idXR0b24+DQoJPGlucHV0IHR5cGU9InRleHQiIGlkPSJjb21waWxlZCIgdmFsdWU9IldhaXRpbmcgZm9yIGlucHV0Li4uIiBkaXNhYmxlZD4NCg0KDQo8Zm9ybSBuYW1lPSJzZXF1ZW5jZUJ1aWxkZXIiIGFjdGlvbj0iIiBtZXRob2Q9ImdldCIgaWQ9ImJpZ0Zvcm0iPiANCg0KDQo8L2Zvcm0+DQoNCjwvYm9keT4NCg0KPGZvb3Rlcj4NCg0KPHNjcmlwdCB0eXBlPSJ0ZXh0L2phdmFzY3JpcHQiPg0KCQ0KCWJ1aWxkRm9ybShudWxsKTsNCgl3cml0ZUhUTUwoZm9ybSk7DQoNCg0KPC9zY3JpcHQ+DQo8L2Zvb3Rlcj4NCg==">
                        The “iframe” tag is not supported by your browser.
                        </iframe>"""
        }



        section("Debug settings", hideable: true){

            input(name:"logDebug", type:"bool", title: "Log debug information?",
                  description: "Logs data for debugging. (Default: Off)", defaultValue: true,
                  required: false, displayDuringSetup: true, submitOnChange: true)
            
            input(name:"logDescriptionText", type:"bool", title: "Log description text?",
                  description: "Logs when useful things happen. (Default: On)", defaultValue: true,
                  required: true, displayDuringSetup: true, submitOnChange: true)
        }
        section("<h2>Help/Information</h2>"){
            paragraph "Thanks for installing MagicHome Manager!"
        }
    }
}
