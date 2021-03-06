/*
 * File: app/view/CabRequestCancel.js
 *
 * This file was generated by Sencha Architect version 2.1.0.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.1.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.1.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('MyApp.view.CabRequestCancel', {
    extend: 'Ext.form.Panel',

    frame: false,
    height: 732,
    width: 794,
    bodyPadding: 10,
    header: true,
    hideCollapseTool: true,
    title: 'Employee Cab Request/Cancellation',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'fieldset',
                    height: 134,
                    width: 374,
                    layout: {
                        align: 'stretch',
                        type: 'vbox'
                    },
                    title: 'EmployeeInfo',
                    items: [
                        {
                            xtype: 'displayfield',
                            maxWidth: 200,
                            fieldLabel: 'Emp. ID'
                        },
                        {
                            xtype: 'displayfield',
                            maxWidth: 400,
                            fieldLabel: 'Emp. Name'
                        },
                        {
                            xtype: 'displayfield',
                            maxWidth: 400,
                            width: 400,
                            fieldLabel: 'Manager Name'
                        },
                        {
                            xtype: 'displayfield',
                            maxWidth: 400,
                            fieldLabel: 'Cost Centre'
                        }
                    ]
                },
                {
                    xtype: 'fieldset',
                    width: 373,
                    title: 'AddressInformation',
                    items: [
                        {
                            xtype: 'displayfield',
                            maxWidth: 250,
                            width: 250,
                            fieldLabel: 'Line 1'
                        },
                        {
                            xtype: 'displayfield',
                            maxWidth: 250,
                            width: 250,
                            fieldLabel: 'Line 2'
                        },
                        {
                            xtype: 'displayfield',
                            maxWidth: 350,
                            width: 263,
                            fieldLabel: 'Landmark'
                        },
                        {
                            xtype: 'displayfield',
                            maxWidth: 350,
                            width: 150,
                            fieldLabel: 'Locality'
                        },
                        {
                            xtype: 'displayfield',
                            maxWidth: 350,
                            width: 150,
                            fieldLabel: 'City'
                        },
                        {
                            xtype: 'displayfield',
                            maxWidth: 350,
                            width: 150,
                            fieldLabel: 'State'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    items: [
                        {
                            xtype: 'tabpanel',
                            border: 1,
                            height: 322,
                            activeTab: 0,
                            items: [
                                {
                                    xtype: 'panel',
                                    height: 254,
                                    width: 770,
                                    title: 'Request',
                                    items: [
                                        {
                                            xtype: 'form',
                                            height: 661,
                                            width: 768,
                                            autoScroll: true,
                                            bodyPadding: 10,
                                            title: 'Request',
                                            items: [
                                                {
                                                    xtype: 'fieldcontainer',
                                                    height: 173,
                                                    width: 422,
                                                    fieldLabel: '',
                                                    items: [
                                                        {
                                                            xtype: 'datefield',
                                                            width: 221,
                                                            fieldLabel: 'Start date'
                                                        },
                                                        {
                                                            xtype: 'timefield',
                                                            fieldLabel: 'Log in Time'
                                                        },
                                                        {
                                                            xtype: 'datefield',
                                                            styleHtmlContent: false,
                                                            width: 221,
                                                            fieldLabel: 'End date',
                                                            allowBlank: false,
                                                            useStrict: true
                                                        },
                                                        {
                                                            xtype: 'timefield',
                                                            fieldLabel: 'Logout Time'
                                                        },
                                                        {
                                                            xtype: 'checkboxgroup',
                                                            height: 52,
                                                            width: 725,
                                                            fieldLabel: 'Reoccur Days',
                                                            items: [
                                                                {
                                                                    xtype: 'checkboxfield',
                                                                    boxLabel: 'Monday'
                                                                },
                                                                {
                                                                    xtype: 'checkboxfield',
                                                                    boxLabel: 'Tuesday'
                                                                },
                                                                {
                                                                    xtype: 'checkboxfield',
                                                                    boxLabel: 'Wednesday'
                                                                },
                                                                {
                                                                    xtype: 'checkboxfield',
                                                                    boxLabel: 'Thursday'
                                                                },
                                                                {
                                                                    xtype: 'checkboxfield',
                                                                    boxLabel: 'Friday'
                                                                },
                                                                {
                                                                    xtype: 'checkboxfield',
                                                                    boxLabel: 'Saturday'
                                                                },
                                                                {
                                                                    xtype: 'checkboxfield',
                                                                    boxLabel: 'Sunday'
                                                                }
                                                            ]
                                                        },
                                                        {
                                                            xtype: 'textareafield',
                                                            width: 351,
                                                            fieldLabel: 'Reason'
                                                        }
                                                    ]
                                                },
                                                {
                                                    xtype: 'button',
                                                    text: 'Make Request'
                                                },
                                                {
                                                    xtype: 'button',
                                                    text: 'Clear'
                                                },
                                                {
                                                    xtype: 'fieldcontainer',
                                                    height: 120,
                                                    fieldLabel: 'Label'
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    title: 'Cancellation',
                                    items: [
                                        {
                                            xtype: 'form',
                                            height: 89,
                                            bodyPadding: 10,
                                            title: 'Filter Request',
                                            items: [
                                                {
                                                    xtype: 'datefield',
                                                    anchor: '100%',
                                                    maxWidth: 200,
                                                    fieldLabel: 'Trip Start Date'
                                                },
                                                {
                                                    xtype: 'datefield',
                                                    anchor: '100%',
                                                    maxWidth: 200,
                                                    fieldLabel: 'Trip End Date'
                                                },
                                                {
                                                    xtype: 'button',
                                                    text: 'Search'
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'gridpanel',
                                            title: 'My Planned Trips',
                                            columns: [
                                                {
                                                    xtype: 'datecolumn',
                                                    dataIndex: 'date',
                                                    text: 'Trip Start Date'
                                                },
                                                {
                                                    xtype: 'datecolumn',
                                                    dataIndex: 'date',
                                                    text: 'Trip End Date'
                                                },
                                                {
                                                    xtype: 'booleancolumn',
                                                    dataIndex: 'bool',
                                                    text: 'Boolean'
                                                },
                                                {
                                                    xtype: 'datecolumn',
                                                    dataIndex: 'date',
                                                    text: 'Cancel date'
                                                },
                                                {
                                                    xtype: 'numbercolumn',
                                                    dataIndex: 'number',
                                                    text: 'Number'
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    text: 'Pick Up',
                                                    editor: {
                                                        xtype: 'checkboxfield',
                                                        boxLabel: 'Box Label'
                                                    }
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    text: 'Drop',
                                                    editor: {
                                                        xtype: 'checkboxfield',
                                                        boxLabel: 'Box Label'
                                                    }
                                                }
                                            ],
                                            viewConfig: {
                                                plugins: [
                                                    Ext.create('Ext.grid.plugin.DragDrop', {
                                                        ptype: 'gridviewdragdrop'
                                                    })
                                                ]
                                            },
                                            plugins: [
                                                Ext.create('Ext.grid.plugin.RowEditing', {
                                                    ptype: 'rowediting'
                                                })
                                            ],
                                            selModel: Ext.create('Ext.selection.CheckboxModel', {

                                            })
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});