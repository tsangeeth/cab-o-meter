/**
 * The main application viewport, which displays the whole application
 * @extends Ext.Viewport
 */
Ext.define('Cabometer.view.Viewport', {
    extend: 'Ext.Viewport',    
    layout: 'fit',
    
    requires: [
        'Cabometer.view.Header',
        'Cabometer.view.book.View',
        'Cabometer.view.book.SideBar',
        'Cabometer.view.review.List'
    ],
    
    initComponent: function() {
        var me = this;
        
        Ext.apply(me, {
            items: [
                {
                    xtype: 'panel',
                    border: false,
                    id    : 'viewport',
                    resizable: true,    
                    layout: {
                        type: 'vbox',
                        align: 'stretch'
                    },
                    
                    dockedItems: [
                        Ext.create('Books.view.Header'),
                        Ext.create('Books.view.book.SideBar')
                        
                    ],
                    
                    items: [
                       
                        Ext.create('Books.view.book.View'),
                        Ext.create('Books.view.review.List')
                    ]
                }
            ]
        });
                
        me.callParent(arguments);
    }
});